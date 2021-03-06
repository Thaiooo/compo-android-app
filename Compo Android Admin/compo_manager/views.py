from compo_admin import settings
from compo_manager.models import ThemeForm, Theme, PackForm, Pack, TeamForm, \
    Team, Match, QuizzPlayer, QuizzPlayerForm, PlayerForm
from compo_manager.services import QuizzPlayerListServices, \
    MatchDisplayerService
from django.contrib.auth.decorators import login_required, permission_required, \
    user_passes_test
from django.contrib.auth.forms import UserCreationForm
from django.contrib.formtools.wizard.views import SessionWizardView
from django.core.files.storage import FileSystemStorage
from django.http.response import HttpResponse, HttpResponseRedirect
from django.shortcuts import render_to_response, get_object_or_404
from django.template import loader
from django.template.context import RequestContext
import datetime
from compo_manager.forms import MatchFormMatchStep3
from django.db import transaction

def superuser_check(user):
    return user.is_superuser

# Main index
@login_required(redirect_field_name='/accounts/login')
def index(request):
    template = loader.get_template('index.html')
    context = RequestContext(request)
    return HttpResponse(template.render(context))


@login_required(redirect_field_name='/accounts/login')
@user_passes_test(superuser_check)
def create_user(request):
    
    if request.method == 'POST':
        form = UserCreationForm(request.POST)
        
        if form.is_valid():
            form.save()
            return HttpResponseRedirect('/user')
    else:
        form = UserCreationForm()
            
    variables = RequestContext(request, {'form':form})
    return render_to_response('create_user.html', variables)

# Theme views
@login_required(redirect_field_name='/accounts/login')
def create_theme(request):
    if request.method == 'POST':
        form = ThemeForm(request.POST)
        if form.is_valid():
            form.save()
            return HttpResponseRedirect('/theme')
    else:
        form = ThemeForm() 
    
    variables = RequestContext(request, {'form':form})
    return render_to_response('create_theme.html', variables)

@login_required(redirect_field_name='/accounts/login')
def update_theme(request, theme_id):
    theme = get_object_or_404(Theme, id=theme_id)
    
    if request.method == 'POST':
        form = ThemeForm(request.POST, instance=theme)
        if form.is_valid():
            form.save()
            return HttpResponseRedirect('/theme')
    else:
        form = ThemeForm(instance=theme) 
        
    variables = RequestContext(request, {'form':form, 'theme_id':theme_id})
    return render_to_response('create_theme.html', variables)

@login_required(redirect_field_name='/accounts/login')
def index_theme(request):
    themes = Theme.objects.all()
    template = loader.get_template('index_theme.html')
    context = RequestContext(request, {'themes':themes})
    return HttpResponse(template.render(context))


# Pack views
@login_required(redirect_field_name='/accounts/login')
def create_pack(request):
    if request.method == 'POST':
        form = PackForm(request.POST)
        if form.is_valid():    
            form.save()
            return HttpResponseRedirect('/pack')
    else:
        form = PackForm() 
        
    variables = RequestContext(request, {'form':form})    
    return render_to_response('create_pack.html', variables)

@login_required(redirect_field_name='/accounts/login')
def update_pack(request, pack_id):
    pack = get_object_or_404(Pack, id=pack_id)
    
    if request.method == 'POST':
        form = PackForm(request.POST, instance=pack)
        if form.is_valid():
            form.save()
            return HttpResponseRedirect('/pack')
    else:
        form = PackForm(instance=pack) 
        
    variables = RequestContext(request, {'form':form, 'pack_id':pack_id})
    return render_to_response('create_pack.html', variables)

@login_required(redirect_field_name='/accounts/login')
def index_pack(request):
    packs = Pack.objects.all()
    template = loader.get_template('index_pack.html')
    context = RequestContext(request, {'packs':packs})
    return HttpResponse(template.render(context))



# Team views
@login_required(redirect_field_name='/accounts/login')
def create_team(request):
    if request.method == 'POST':
        form = TeamForm(request.POST)
        if form.is_valid():    
            form.save()
            
            return HttpResponseRedirect('/team')
    else:
        form = TeamForm() 
        
    variables = RequestContext(request, {'form':form})
    return render_to_response('create_team.html', variables)

@login_required(redirect_field_name='/accounts/login')
def update_team(request, team_id):
    team = get_object_or_404(Team, id=team_id)
    
    if request.method == 'POST':
        form = TeamForm(request.POST, instance=team)
        if form.is_valid():
            form.save()
            return HttpResponseRedirect('/team')
    else:
        form = TeamForm(instance=team) 
        
    variables = RequestContext(request, {'form':form, 'team_id':team_id})
    return render_to_response('create_team.html', variables)

@login_required(redirect_field_name='/accounts/login')
def index_team(request):
    teams = Team.objects.all()
    template = loader.get_template('index_team.html')
    context = RequestContext(request, {'teams':teams})
    return HttpResponse(template.render(context))


# Match views
class MatchWizard(SessionWizardView):
    
    template_name = 'create_match.html'
    file_storage = FileSystemStorage(location=settings.MEDIA_ROOT)
    
    @transaction.commit_on_success
    def done(self, form_list, **kwargs):
        
        match = Match()
        
        teams_form = form_list[0]
        
        if teams_form.is_valid():
            home_team = teams_form.cleaned_data['home_team']
            away_team = teams_form.cleaned_data['away_team']
        
        
        file_form = form_list[1]
            
        if file_form.is_valid():
            compo_file = file_form.cleaned_data['file']
        
        match_form = form_list[2]
        
        if match_form.is_valid():
            match.is_valid = False
            match.update_time = datetime.datetime.now()
            match.name = match_form.cleaned_data['name']
            match.date = match_form.cleaned_data['date']
            match.score_away = match_form.cleaned_data['score_away']
            match.score_home = match_form.cleaned_data['score_home']
            match.is_overtime = match_form.cleaned_data['is_overtime']
            match.sog_home = match_form.cleaned_data['sog_home']
            match.sog_away = match_form.cleaned_data['sog_away']
            match.pack = match_form.cleaned_data['pack']
            match.order_number = match_form.cleaned_data['order_number']
            
            match.save()
            
            service = QuizzPlayerListServices() 
            service.insert_unknown_players = True
        
            service.get_quizzplayers_from_file(compo_file, home_team, away_team, match)
            
        return HttpResponseRedirect('/match')

@login_required(redirect_field_name='/accounts/login')
def update_match(request, match_id):
    match = get_object_or_404(Match, id=match_id)
    
    service = MatchDisplayerService() 
    
    match_displayer = service.get_match_displayer(match)
    
    if request.method == 'POST':
        form = MatchFormMatchStep3(request.POST, instance=match)
    
        if form.is_valid():
            match.is_valid = False
            form.save()
            return HttpResponseRedirect('/match')
    else:
        form = MatchFormMatchStep3(instance=match)
    
    variables = RequestContext(request, {'match_displayer':match_displayer,'form':form})
    return render_to_response('update_match.html', variables)

@login_required(redirect_field_name='/accounts/login')
@permission_required(perm='compo_manager.validate')
def validate_match(request, match_id):
    
    if request.user.has_perm('compo_manager.validate'):
        match = get_object_or_404(Match, id=match_id)
        match.is_valid = True
        match.save()
    
    return HttpResponseRedirect('/match')
 
@login_required(redirect_field_name='/accounts/login')    
def index_match(request):
    matchs = Match.objects.all()
    template = loader.get_template('index_match.html')
    context = RequestContext(request, {'matchs':matchs})
    return HttpResponse(template.render(context))

@login_required(redirect_field_name='/accounts/login')    
def update_quizzplayer(request, quizzplayer_id):
    quizzplayer = get_object_or_404(QuizzPlayer, id=quizzplayer_id)
    if request.method == 'POST':
        quizzplayer_form = QuizzPlayerForm(request.POST, instance=quizzplayer)
        player_form = PlayerForm(request.POST, instance=quizzplayer.player)
        if quizzplayer_form.is_valid():
            quizzplayer_form.save()
        
        if player_form.is_valid():
            player_form.save()
            return HttpResponseRedirect('/match/update/%i'%quizzplayer.match.id)
    else:
        quizzplayer_form = QuizzPlayerForm(instance=quizzplayer)
        player_form = PlayerForm(instance=quizzplayer.player)
    
    variables = RequestContext(request, {'quizzplayer_form':quizzplayer_form, 'quizzplayer_id':quizzplayer_id, 
                                         'player_form':player_form,'match_id':quizzplayer.match.id})
    return render_to_response('update_quizzplayer.html', variables)