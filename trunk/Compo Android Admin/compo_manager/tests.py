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
        self.home_team = Team.objects.create(name='Argentina', code='ARG')
        self.away_team = Team.objects.create(name='Germany', code='GER')
        self.compo_file = [
                           'HOME;Romero;GK;C',
                           'HOME;Otamendi;D;R',
                           'HOME;Demichelis;D;C',
                           'HOME;_Burdisso;D;C',
                           'HOME;Heinze;D;L',
                           'HOME;Rodriguez;MD;C',
                           'HOME;Mascherano;MD;C',
                           'HOME;di Maria;F;R',
                           'HOME;_Messi;F;C',
                           'HOME;Higuain;F;L',
                           'HOME;Tevez;S;C',
                           'HOME;Maradona;C;C',
                           'AWAY;Neuer;GK;C',
                           'AWAY;_Lahm;D;R',
                           'AWAY;Mertesacker;D;C',
                           'AWAY;Friedrich*;D;C',
                           'AWAY;Boateng;D;L',
                           'AWAY;Khedira;MD;C',
                           'AWAY;_Schweinsteiger;MD;C',
                           'AWAY;Muller*;F;R',
                           'AWAY;_Ozil;MO;C',
                           'AWAY;Podolski;F;L',
                           'AWAY;Klose**;S;C',
                           'AWAY;Low;C;C',
                           ]
    
    def test_quizzplayers_from_file(self):
        service = QuizzPlayerListServices()
        service.insert_unknown_players = True
        
        quizzplayer_list = service.get_quizzplayers_from_file(self.compo_file, self.home_team, self.away_team)
        
        for p in quizzplayer_list:
            print ('%s (%i, %i)'%(p.player.name, p.x, p.y))
        
        self.assertEqual(len(quizzplayer_list), 24, None)
        
        