from django.http.response import HttpResponse, HttpResponseRedirect
from django.template import loader
from django.template.context import RequestContext
from compo_manager.models import ThemeForm, Theme, PackForm, Pack, TeamForm,\
    Team, Match
from django.shortcuts import render_to_response, get_object_or_404
from django.contrib.formtools.wizard.views import SessionWizardView
import datetime
from django.core.files.storage import FileSystemStorage
from compo_admin import settings
from compo_manager.services import QuizzPlayerListServices,\
    MatchDisplayerService
from django.contrib.auth.decorators import login_required

# Main index
@login_required(redirect_field_name='/accounts/login')
def index(request):
    template = loader.get_template('index.html')
    context = RequestContext(request)
    return HttpResponse(template.render(context))

# Theme views
@login_required(redirect_field_name='/accounts/login')
def create_theme(request):
    if request.method == 'POST':
        form = ThemeForm(request.POST)
        if form.is_valid():
            name = form.cleaned_data['name']
            code = name.replace(' ','').upper()
            
            theme = Theme()
            theme.name = name
            theme.code = code
                
            theme.save()
            
            return HttpResponseRedirect('/theme')
    else:
        form = ThemeForm() 
    
    variables = RequestContext(request, {'form':form})
    return render_to_response('create_theme.html', variables)

@login_required(redirect_field_name='/accounts/login')
def update_theme(request, theme_id):
    theme = get_object_or_404(Theme, id=theme_id)
    form = ThemeForm(request.POST, instance=theme)
    
    if form.is_valid():
            name = form.cleaned_data['name']
            code = name.replace(' ','').upper()
            
            theme.name = name
            theme.code = code
            
            theme.save()
            
            return HttpResponseRedirect('/theme')
    else:
        form = ThemeForm(instance=theme) 
        
    variables = RequestContext(request, {'form':form, 'theme_id':theme_id})
    return render_to_response('create_theme.html', variables)

@login_required(redirect_field_name='/accounts/login')
def delete_theme(request, theme_id):
    theme = get_object_or_404(Theme, id=theme_id)
    theme.delete()
    
    return HttpResponseRedirect('/theme')


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
    form = PackForm(request.POST, instance=pack)
    if form.is_valid():
        form.save()
        return HttpResponseRedirect('/pack')
    else:
        form = PackForm(instance=pack) 
        
    variables = RequestContext(request, {'form':form, 'pack_id':pack_id})
    return render_to_response('create_pack.html', variables)

@login_required(redirect_field_name='/accounts/login')
def delete_pack(request, pack_id):
    pack = get_object_or_404(Pack, id=pack_id)
    pack.delete()
    
    return HttpResponseRedirect('/pack')


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
            name = form.cleaned_data['name']
            code = name.replace(' ','').upper()
            
            team = Team()
            team.name = name
            team.code = code
            
            team.save()
            
            return HttpResponseRedirect('/team')
    else:
        form = TeamForm() 
        
    variables = RequestContext(request, {'form':form})
    return render_to_response('create_team.html', variables)

@login_required(redirect_field_name='/accounts/login')
def update_team(request, team_id):
    team = get_object_or_404(Team, id=team_id)
    form = TeamForm(request.POST, instance=team)
    if form.is_valid():
        name = form.cleaned_data['name']
        code = name.replace(' ','').upper()
            
        team.name = name
        team.code = code
            
        team.save()
        
        return HttpResponseRedirect('/team')
    else:
        form = TeamForm(instance=team) 
        
    variables = RequestContext(request, {'form':form, 'team_id':team_id})
    return render_to_response('create_team.html', variables)

@login_required(redirect_field_name='/accounts/login')
def delete_team(request, team_id):
    team = get_object_or_404(Team, id=team_id)
    team.delete()
    return HttpResponseRedirect('/team')

def index_team(request):
    teams = Team.objects.all()
    template = loader.get_template('index_team.html')
    context = RequestContext(request, {'teams':teams})
    return HttpResponse(template.render(context))


# Match views
class MatchWizard(SessionWizardView):
    
    template_name = 'create_match.html'
    file_storage = FileSystemStorage(location=settings.MEDIA_ROOT)
    
    @login_required(redirect_field_name='/accounts/login')
    def done(self, form_list, **kwargs):
        match = Match()
        
        teams_form = form_list[0]
        
        if teams_form.is_valid():
            home_team = teams_form.cleaned_data['home_team']
            away_team = teams_form.cleaned_data['away_team']
        
        
        file_form = form_list[1]
            
        if file_form.is_valid():
            compo_file = file_form.cleaned_data['file']
        
        service = QuizzPlayerListServices() 
        service.insert_unknown_players = True
        
        quizzplayers = service.get_quizzplayers_from_file(compo_file, home_team, away_team)
        
        match_form = form_list[2]
        
        if match_form.is_valid():
            match.is_valid = False
            match.update_time = datetime.datetime.now()
            match.name = match_form.cleaned_data['name']
            match.date = match_form.cleaned_data['date']
            match.score_away = match_form.cleaned_data['score_away']
            match.score_home = match_form.cleaned_data['score_home']
            match.pack = match_form.cleaned_data['pack']
            
            match.save()
            
            match.quizz_players = quizzplayers
            
        return HttpResponseRedirect('/match')

@login_required(redirect_field_name='/accounts/login')
def update_match(request, match_id):
    match = get_object_or_404(Match, id=match_id)
    
    service = MatchDisplayerService() 
    
    match_displayer = service.get_match_displayer(match)
    
    variables = RequestContext(request, {'match_displayer':match_displayer})
    return render_to_response('update_match.html', variables)

@login_required(redirect_field_name='/accounts/login')
def validate_match(request, match_id):
    match = get_object_or_404(Match, id=match_id)
    match.is_valid = True
    match.save()
    
    return HttpResponseRedirect('/match')

@login_required(redirect_field_name='/')    
def delete_match(request, match_id):
    match = get_object_or_404(Match, id=match_id)
    
    for quizzplayer in match.quizz_players.all():
        quizzplayer.delete()
    
    match.delete()
    return HttpResponseRedirect('/match')
    
    
def index_match(request):
    matchs = Match.objects.order_by('is_valid')
    template = loader.get_template('index_match.html')
    context = RequestContext(request, {'matchs':matchs})
    return HttpResponse(template.render(context))
