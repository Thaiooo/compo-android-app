"""
This file demonstrates writing tests using the unittest module. These will pass
when you run "manage.py test".

Replace this with more appropriate tests for your application.
"""

from django.test import TestCase
from compo_manager.models import Team
from compo_manager.services import QuizzPlayerListServices


class QuizzPlayerListServiceTestCase(TestCase):
    
    def setUp(self):
        self.home_team = Team.objects.create(name='Home', code='HOME')
        self.away_team = Team.objects.create(name='Away', code='AWAY')
        self.compo_file = [
                           'HOME\tRomero\tGK\tC',
                           'HOME\tOtamendi\tD\tR',
                           'HOME\tDemichelis\tD\tC',
                           'HOME\t_Burdisso\tD\tC',
                           'HOME\tHeinze\tD\tL',
                           'HOME\tRodriguez\tMD\tC',
                           'HOME\tMascherano\tMD\tC',
                           'HOME\tdi Maria\tF\tR',
                           'HOME\t_Messi\tF\tC',
                           'HOME\tHiguain\tF\tL',
                           'HOME\tTevez\tS\tC',
                           'HOME\tMaradona\tC\tC',
                           'AWAY\tNeuer\tGK\tC',
                           'AWAY\t_Lahm\tD\tR',
                           'AWAY\tMertesacker\tD\tC',
                           'AWAY\tFriedrich*\tD\tC',
                           'AWAY\tBoateng\tD\tL',
                           'AWAY\tKhedira\tMD\tC',
                           'AWAY\t_Schweinsteiger\tMD\tC',
                           'AWAY\tMuller*\tF\tR',
                           'AWAY\t_Ozil\tMD\tC',
                           'AWAY\tPodolski\tF\tL',
                           'AWAY\tKlose**\tS\tC',
                           'AWAY\tLow\tC\tC',
                           ]
    
    def test_quizzplayers_from_file(self):
        service = QuizzPlayerListServices()
        service.insert_unknown_players = True
        
        quizzplayer_list = service.get_quizzplayers_from_file(self.compo_file, self.home_team, self.away_team)
        
        for p in quizzplayer_list:
            print ('%s\t%i\t%i'%(p.player.name, p.x, p.y))
        
        self.assertEqual(len(quizzplayer_list), 24, None)
        
        