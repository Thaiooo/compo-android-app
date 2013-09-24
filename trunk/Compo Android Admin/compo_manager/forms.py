from django.forms.forms import Form
from django.forms.models import ModelChoiceField, ModelForm
from compo_manager.models import Team, Match
from django.forms.fields import FileField

class MatchFormTeamsStep1(Form):
    home_team = ModelChoiceField(queryset=Team.objects.all())
    away_team = ModelChoiceField(queryset=Team.objects.all())

    
class MatchFormUploadQuizzPlayersStep2(Form):
    file = FileField()

    
class MatchFormMatchStep3(ModelForm):
    
    class Meta:
        model = Match
        fields = ('score_home', 'score_away', 'is_overtime', 'sog_home', 'sog_away', 'name', 'date', 'pack')


