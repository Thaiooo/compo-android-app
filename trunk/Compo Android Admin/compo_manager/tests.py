"""
This file demonstrates writing tests using the unittest module. These will pass
when you run "manage.py test".

Replace this with more appropriate tests for your application.
"""

from django.test import TestCase
from compo_manager.models import Team, Match, Pack, Theme
from compo_manager.services import QuizzPlayerListServices
import datetime


class QuizzPlayerListServiceTestCase(TestCase):
    
    def setUp(self):
        
        theme = Theme()
        theme.code = 'Code'
        theme.name = 'Name'
        theme.credit_limit = 0
        theme.lock = False
        theme.order_number = 0
        
        theme.save()
        
        pack = Pack()
        pack.name = 'Name'
        pack.description = 'Desc'
        pack.order_number = 0
        pack.theme = theme
        
        pack.save()
        
        self.home_team = Team.objects.create(name='Home')
        self.away_team = Team.objects.create(name='Away')
        self.match = Match()
        self.match.date = datetime.date.today()
        self.match.name = 'Match'
        self.match.order_number = 0
        self.match.is_valid = False
        self.match.score_away = 0
        self.match.score_home = 0
        self.match.update_time = datetime.datetime.now()
        self.match.pack = pack 
        
        self.match.save()
        
        self.compo_file = [
                           'HOME\tRomero\tGK\tC\t',
                           'HOME\tOtamendi\tD\tR\t',
                           'HOME\tDemichelis\tD\tC\t',
                           'HOME\t_Burdisso\tD\tC\t',
                           'HOME\tHeinze\tD\tL\t',
                           'HOME\tRodriguez\tMD\tC\t',
                           'HOME\tMascherano\tMD\tC\t',
                           'HOME\tdi Maria\tF\tR\t',
                           'HOME\t_Messi+\tF\tC\thint',
                           'HOME\tHiguain\tF\tL\t',
                           'HOME\tTevez\tS\tC\t',
                           'HOME\tMaradona\tC\tC\t',
                           'AWAY\tNeuer\tGK\tC\t',
                           'AWAY\t_Lahm+\tD\tR\thint',
                           'AWAY\tMertesacker\tD\tC\t',
                           'AWAY\tFriedrich*\tD\tC\t',
                           'AWAY\tBoateng\tD\tL\t',
                           'AWAY\tKhedira\tMD\tC\t',
                           'AWAY\t_Schweinsteiger\tMD\tC\t',
                           'AWAY\tMuller*\tF\tR\t',
                           'AWAY\t_Ozil\tMD\tC\thint',
                           'AWAY\tPodolski\tF\tL\t',
                           'AWAY\tKlose**\tS\tC\t',
                           'AWAY\tLow\tC\tC\t',
                           ]
    
    def test_quizzplayers_from_file(self):
        service = QuizzPlayerListServices()
        service.insert_unknown_players = True
        
        service.get_quizzplayers_from_file(self.compo_file, self.home_team, self.away_team, self.match)
        
        quizzplayer_list = self.match.quizzplayer_set.all()
        
        for p in quizzplayer_list:
            print ('%s\t%i\t%i'%(p.player.name, p.x, p.y))
        
        self.assertEqual(len(quizzplayer_list), 24, None)
        
        