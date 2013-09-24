from django.db import models
from django.forms.models import ModelForm

#********************* Models ***********************
class Theme(models.Model):
    code = models.CharField(max_length=100, unique=True)
    name = models.CharField(max_length=250, unique=True)
    order_number = models.IntegerField(null=True, unique=True)
    lock = models.BooleanField()
    credit_limit = models.IntegerField()
    
    def __unicode__(self):
        return self.name


class Pack(models.Model):
    name = models.CharField(max_length=100, unique=True)
    description = models.CharField(max_length=250, null=True)
    order_number = models.IntegerField(null=True, unique=True)
    theme = models.ForeignKey(Theme)
    
    def __unicode__(self):
        return self.name + ' ' + self.theme.name
    
    
class Team(models.Model):
    
    COLORS = (
              ('WHITE','White'),
              ('BLUE', 'Blue'),
              ('RED', 'Red'),
              ('YELLOW', 'Yellow'),
              ('BLACK', 'Black'),
              ('GREEN', 'Green'),
              ('PURPLE', 'Purple'),
              ('ORANGE', 'Orange'),
              )
    
    name = models.CharField(max_length=100, unique=True)
    code = models.CharField(max_length=10, unique=True)
    home_short_color = models.CharField(max_length=10, choices=COLORS)
    home_sock_color = models.CharField(max_length=10, choices=COLORS)
    home_jersey_color = models.CharField(max_length=10, choices=COLORS)
    away_short_color = models.CharField(max_length=10, choices=COLORS)
    away_jersey_color = models.CharField(max_length=10, choices=COLORS)
    away_sock_color = models.CharField(max_length=10, choices=COLORS)
    
    def __unicode__(self):
        return self.name
    
        
class Player(models.Model):
    name = models.CharField(max_length=100)         

    def __unicode__(self):
        return self.name


class QuizzPlayer(models.Model):
    x = models.FloatField()
    y = models.FloatField()
    is_hide = models.BooleanField()
    is_home = models.BooleanField()
    is_coach = models.BooleanField()
    goal = models.IntegerField()
    csc = models.IntegerField()
    earn_credit = models.IntegerField()
    hint = models.CharField(max_length=500)
    team = models.ForeignKey(Team)
    player = models.ForeignKey(Player)

    def __unicode__(self):
        return self.player.name


class Match(models.Model):
    
    class Meta:
        permissions = (('validate', 'Can validate a match'))
        
        
    score_home = models.IntegerField()
    score_away = models.IntegerField()
    name = models.CharField(max_length=100, unique=True)
    date = models.DateField()
    pack = models.ForeignKey(Pack)
    is_valid = models.BooleanField()
    update_time = models.DateTimeField()
    quizz_players = models.ManyToManyField(QuizzPlayer)
    
    def __unicode__(self):
        return self.name

   
#********************* Forms *********************** 
class ThemeForm(ModelForm):
     
    class Meta:
        model = Theme
        
        
class PackForm(ModelForm):
    
    class Meta:
        model = Pack
        
class TeamForm(ModelForm):
    
    class Meta:
        model = Team
