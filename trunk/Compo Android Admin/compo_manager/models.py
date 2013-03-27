from django.db import models

class Pack(models.Model):
    name = models.CharField(max_length=100)
    description = models.CharField(max_length=250)
    score_limit = models.IntegerField()
    credit_limit = models.IntegerField()

    def __unicode__(self):
        return self.name

class Team(models.Model):
    name = models.CharField(max_length=100)
    code = models.CharField(max_length=10)
    
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
    team = models.ForeignKey(Team)
    player = models.ForeignKey(Player)

class Quizz(models.Model):
    score_home = models.IntegerField()
    score_away = models.IntegerField()
    name = models.CharField(max_length=100)
    description = models.CharField(max_length=250)
    stadium = models.CharField(max_length=100)
    date = models.DateField()
    level = models.CharField(max_length=15)
    pack = models.ForeignKey(Pack)
    is_valid = models.BooleanField()
    update_time = models.DateTimeField()
    quizz_players = models.ManyToManyField(QuizzPlayer)
    
    def __unicode__(self):
        return self.name
    
class User(models.Model):
    name = models.CharField(max_length=100)
    email = models.CharField(max_length=100)
    point = models.IntegerField()
    credit = models.IntegerField()
    overall_time = models.IntegerField()
    
    def __unicode__(self):
        return self.name
    
class Play(models.Model):
    value = models.IntegerField()
    time = models.IntegerField()
    quizz = models.ForeignKey(Quizz)
    user = models.ForeignKey(User)
    
    

    