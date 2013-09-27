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

CAPTAIN_PLAYER = '+'

TEAM = 0

NAME = 1

POSITION = 2

SIDE = 3

HINT = 4

SEPARATOR = '\t'

NB_LINES = 24

MAP_SEP= '|'

class ServiceError(BaseException):
    pass

class QuizzPlayerListServices:
    
    def __init__(self):
         
        self.field_map = {}
        
        self.insert_unknown_players = False
        
    
    def get_quizzplayers_from_file(self, compo_file, home_team, away_team):
        cpt = 0
        # Check the file
        for line in compo_file:
            upper_line = line.upper()
            splitted_line = upper_line.strip('\n\r').split(SEPARATOR)
            is_valid = self.__process_line_checking(splitted_line)
            
            if is_valid:
                cpt = cpt + 1
                self.__fill_position_map(splitted_line)
        
        # Check lines counting    
        if cpt != NB_LINES:
            raise ServiceError('File contents %i lines instead of %i lines'%(cpt, NB_LINES))
        
        result = self.__compute_quizzplayers(home_team, away_team)
            
        return result
    
    
    def __process_line_checking(self, line):
        
        # Check team
        if TEAMS.count(line[TEAM]) == 0:
            raise ServiceError('Team has to be HOME or AWAY not %s'%line[TEAM])
        
        # Check if player is already stored in database
        current_name = self.__get_player_name(line[NAME])
        
        try:
            current_name.decode('ascii')
        except:
            raise ServiceError('%s contains non ASCII character'%current_name)
        
        current_player = Player.objects.filter(name=current_name)
        
        if not current_player:
            if self.insert_unknown_players:
                current_player = Player()
                current_player.name = current_name
                current_player.save()
            else:
                raise ServiceError('%s unkown in database and you have selected non unknown player insertion option'%current_name)
        
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
        current_value = line[NAME].upper() + MAP_SEP + line[HINT]
        
        if current_key in self.field_map:
            self.field_map[current_key].append(current_value)
        else:
            self.field_map[current_key] = [current_value]
            
        
    
    def __get_player_name(self, name):
        return name.replace(HIDDEN_PLAYER, '').replace(GOAL_PLAYER, '').replace(OG_PLAYER, '').replace(CAPTAIN_PLAYER, '').upper()
    
    
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
            mid = int((range_x[0]+range_x[1])/2)
            x_s = []
            
            if players_nb%2 != 0:
                x_s.append(mid)
            
            cpt=1
            x_min = range_x[0]
            x_max = 0
            while cpt < players_nb:
                mid = int((x_min+x_max)/2)
                x_s.append(mid)
                x_s.append(-mid)
                x_min = mid
                cpt = cpt + 1
            
            cpt = 0
            for p in player_list:
                
                team = away_team
                if team_name == 'HOME':
                    team = home_team
                
                current_table = p.split(MAP_SEP)
                current_name = current_table[0]
                current_hint = current_table[1]
                    
                quizzplayer = self.__get_quizzplayer(current_name, current_hint, team, position)
                
                if position != 'C':
                    current_x = x_s[cpt]
                
                    if team_name == 'HOME':
                        current_x = -current_x
                
                    quizzplayer.x = current_x
                    quizzplayer.y = current_y
                
                # Compute the is home ?
                if team_name == 'HOME':
                    quizzplayer.is_home = True
                else:
                    quizzplayer.is_home = False
                
                # Credits for hint
                quizzplayer.credit_to_unlock_half = 0
                quizzplayer.credit_to_unlock_hint = 0
                quizzplayer.credit_to_unlock_random = 0
                quizzplayer.credit_to_unlock_response = 0
                
                quizzplayer.save()
                
                result.append(quizzplayer)
                
                cpt = cpt + 1
            
        return result
                
    
    def __get_quizzplayer(self, name, hint, team, position):
        
        quizzplayer = QuizzPlayer()
        
        player = Player.objects.get(name=self.__get_player_name(name))
        
        quizzplayer.player = player
        
        quizzplayer.hint = hint
        
        quizzplayer.team = team
            
        quizzplayer.earn_credit = 1
        
        if HIDDEN_PLAYER in name:
            quizzplayer.is_hide = True
        else:
            quizzplayer.is_hide = False
            
        quizzplayer.goal = name.count(GOAL_PLAYER)
        
        quizzplayer.csc = name.count(OG_PLAYER)
        
        quizzplayer.is_captain = CAPTAIN_PLAYER in name
        
        if position == 'C':
            quizzplayer.is_coach = True
            quizzplayer.x = 0
            quizzplayer.y = 0
        else:
            quizzplayer.is_coach = False
            
        return quizzplayer


class QuizzPlayerDisplayer():
    
    def __init__(self, quizzplayer):
        
        self.id = quizzplayer.id
        
        if not quizzplayer.is_coach:
            self.x = int((quizzplayer.x + MAX_X)*10 - 25)
            self.y = int(quizzplayer.y*10 - 12)
        else:
            if quizzplayer.is_home:
                self.x = 0
                self.y = 0
            else:
                self.x = 680
                self.y = 1000
        
        self.is_hide = quizzplayer.is_hide
        
        self.is_home = quizzplayer.is_home
        
        self.is_coach = quizzplayer.is_coach
        
        self.goals = quizzplayer.goal
        
        self.og = quizzplayer.csc
        
        self.earn_credit = quizzplayer.earn_credit
        
        self.name = quizzplayer.player.name


class MatchDisplayer():
    
    def __init__(self, match):
        
        self.id = match.id
        
        self.score_away = match.score_away
        
        self.score_home = match.score_home
        
        self.is_overtime = match.is_overtime
        
        self.sog_home = match.sog_home
        
        self.sog_away = match.sog_away
        
        self.name = match.name
        
        self.date = match.date
        
        self.is_valid = match.is_valid
        
        self.quizzplayer_displayer = []
        
        for quizzplayer in match.quizz_players.all():
            
            if quizzplayer.is_home:
                self.home_team = quizzplayer.team
            else:
                self.away_team = quizzplayer.team
            
            quizzplayer_displayer = QuizzPlayerDisplayer(quizzplayer)
            self.quizzplayer_displayer.append(quizzplayer_displayer)

class MatchDisplayerService():
    
    def get_match_displayer(self, match):    
        match_displayer = MatchDisplayer(match)
        
        return match_displayer
        