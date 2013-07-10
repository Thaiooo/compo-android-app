from django.forms.forms import Form
from django.forms.models import ModelChoiceField, ModelForm
from compo_manager.models import Pack, Team, Match

class MatchFormPackStep1(Form):
    pack = ModelChoiceField(queryset=Pack.objects.all())

    
class MatchFormTeamsStep2(Form):
    home_team = ModelChoiceField(queryset=Team.objects.all())
    away_team = ModelChoiceField(queryset=Team.objects.all())

    
class MatchFormFieldsStep3(ModelForm):
    
    class Meta:
        model = Match
        fields = ('score_home', 'score_away', 'name', 'date')


