from compo_manager.models import Player

POSITIONS_TYPE = ('GK','L','D','MD','M','MO','F','S','C')

POSITIONS_SIDEABLE = ('L','D','MD','M','MO','F','S')

SIDES = ('L','C','R')

NAME = 0

POSITION = 1

SIDE = 2

GOAL = 3

SEPARATOR = ';'

class QuizzPlayerListServices:
    
    def __init__(self):
        
        keyset = []
        
        for pt in POSITIONS_SIDEABLE:
            for s in SIDES:
                keyset.append(pt+s)
        
        valueset = len(keyset)*[0]
        
        self.away_positions_nb = dict(zip(keyset,valueset))
        
        self.home_positions_nb = dict(zip(keyset,valueset))
        
        self.unknown_player_list = []
        
        self.insert_unknown_players = False
        
    
    def get_quizzplayers_from_file(self, compo_file, home_team, away_team):
    
        for line in compo_file:
            self.__process_line_checking(line.split(SEPARATOR))
    
    
    def __process_line_checking(self, line):
        
        # Check if player is already stored in database
        current_player = Player.objects.get(name=line[NAME])
        
        if not current_player:
            self.unknown_player_list.append(current_player)
        
        # Check position    
        if POSITIONS_TYPE.count(line[POSITION]) == 0:
            raise ValueError('Positions %s does not exist'%line[POSITION]) 
        
        # Check side
        if SIDES.count(line[SIDE]) == 0:
            raise ValueError('Side %s does not exist'%line[SIDE]) 