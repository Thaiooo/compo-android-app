from compo_manager.models import Player, QuizzPlayer

POSITIONS_TYPE = ('GK','L','D','MD','M','MO','F','S','C')

MAX_Y = 100

MIN_Y = 0 

MAX_X = 34

MIN_X = -34

POSITIONS_Y = {'GK':0, 'L':1, 'D':2, 'MD':3, 'M': 4, 'MO':5, 'F':6, 'S':7}

POSITION_X = {'L':[-34,-18], 'C':[-17,17], 'R':[18,34]}

DISTANCE_Y = 6

POSITIONS_SIDEABLE = ('L','D','MD','M','MO','F','S')

SIDES = ('L','C','R')

TEAMS = ('HOME','AWAY')

HIDDEN_PLAYER = '_'

GOAL_PLAYER = '*'

OG_PLAYER = '-'

TEAM = 0

NAME = 1

POSITION = 2

SIDE = 3

SEPARATOR = ';'

NB_LINES = 24

MAP_SEP= '|'

class ServiceError(BaseException):
    pass

class QuizzPlayerListServices:
    
    def __init__(self):
         
        self.field_map = {}
        
        self.unknown_player_list = []
        
        self.insert_unknown_players = False
        
    
    def get_quizzplayers_from_file(self, compo_file, home_team, away_team):
        result = []
        
        cpt = 0
        # Check the file
        for line in compo_file:
            splitted_line = line.split(SEPARATOR)
            is_valid = self.__process_line_checking(splitted_line)
            
            if is_valid:
                cpt = cpt + 1
                self.__fill_position_map(splitted_line)
        
        # Check lines counting    
        if cpt != NB_LINES:
            raise ServiceError('File contents %i lines instead of %i lines'%(cpt, NB_LINES))
        
        # Insert unknown players in database
        if self.insert_unknown_players:
            self.__store_unknown_players()
        else:
            return result
        
        result = self.__compute_quizzplayers(home_team, away_team)
            
        return result
        
        
    def __store_unknown_players(self):
        
        for player_name in self.unknown_player_list:
            player = Player()
            player.name = player_name
            player.save()
    
    
    def __process_line_checking(self, line):
        
        # Check team
        if TEAMS.count(line[TEAM]) == 0:
            raise ServiceError('Team has to be HOME or AWAY not %s'%line[TEAM])
        
        # Check if player is already stored in database
        current_name = self.__get_player_name(line[NAME])
        current_player = Player.objects.filter(name=current_name)
        
        if not current_player:
            self.unknown_player_list.append(current_name)
        
        # Check position    
        if POSITIONS_TYPE.count(line[POSITION]) == 0:
            raise ServiceError('No position type %s'%line[POSITION])
            return False 
        
        # Check side
        if SIDES.count(line[SIDE]) == 0:
            raise ServiceError('No side %s'%line[SIDE])
            return False

        return True
   
    
    def __fill_position_map(self, line):
        current_key = line[TEAM] + MAP_SEP + line[POSITION] + MAP_SEP + line[SIDE]
        current_name = line[NAME]
        
        if current_key in self.field_map:
            self.field_map[current_key].append(current_name)
        else:
            self.field_map[current_key] = [current_name]
            
        
    
    def __get_player_name(self, name):
        return name.replace(HIDDEN_PLAYER, '').replace(GOAL_PLAYER, '').replace(OG_PLAYER, '')
    
    
    def __compute_quizzplayers(self, home_team, away_team):
        result = []
        
        for key in self.field_map:
            
            player_list = self.field_map[key]
            
            # Decompose the key in team_name, position, side
            key_table = key.split(MAP_SEP)
            team_name = key_table[0]
            position = key_table[1]
            side = key_table[2]
            
            # Compute the y-coordinate
            if position in POSITIONS_Y:
                current_y = POSITIONS_Y[ position ]*DISTANCE_Y
        
                if team_name == 'AWAY':
                    current_y = MAX_Y - current_y
            else:
                current_y = 0
            
            # Compute the x-coordinate
            players_nb = len(player_list)
            range_x = POSITION_X[side]
            distance_x = int((range_x[1]-range_x[0])/players_nb)
            
            cpt=0
            for p in player_list:
                
                team = away_team
                if team_name == 'HOME':
                    team = home_team
                    
                quizzplayer = self.__get_quizzplayer(p, team, position)
                
                if position != 'C':
                    current_x = (range_x[0]+range_x[1])*.5+cpt*distance_x*.5
                
                    if team_name == 'HOME':
                        current_x = -current_x
                
                    quizzplayer.x = current_x
                    quizzplayer.y = current_y
                
                    cpt = cpt + 1
                
                quizzplayer.save()
                
                result.append(quizzplayer)
            
        return result
                
    
    def __get_quizzplayer(self, name, team, position):
        
        quizzplayer = QuizzPlayer()
        
        player = Player.objects.get(name=self.__get_player_name(name))
        
        quizzplayer.player = player
        
        quizzplayer.team = team
            
        quizzplayer.earn_credit = 1
        
        if HIDDEN_PLAYER in name:
            quizzplayer.is_hide = True
        else:
            quizzplayer.is_hide = False
            
        quizzplayer.goals = name.count(GOAL_PLAYER)
        
        quizzplayer.og = name.count(OG_PLAYER)
        
        if position == 'C':
            quizzplayer.is_coach = True
            quizzplayer.x = 0
            quizzplayer.y = 0
        else:
            quizzplayer.is_coach = False
            
        return quizzplayer
            
            
        