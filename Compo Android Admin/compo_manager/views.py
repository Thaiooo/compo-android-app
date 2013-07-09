from django.http.response import HttpResponse, HttpResponseRedirect
from django.template import loader
from django.template.context import RequestContext
from compo_manager.models import ThemeForm, Theme, PackForm, Pack, TeamForm,\
    Team
from django.shortcuts import render_to_response, get_object_or_404

# Main index
def index(request):
    template = loader.get_template('index.html')
    context = RequestContext(request)
    return HttpResponse(template.render(context))


# Theme views
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

def update_theme(request, theme_id):
    theme = get_object_or_404(Theme, id=theme_id)
    form = ThemeForm(request.POST, instance=theme)
    if form.is_valid():
        form.save()
        return HttpResponseRedirect('/theme')
    else:
        form = ThemeForm(instance=theme) 
        variables = RequestContext(request, {'form':form, 'theme_id':theme_id})
    
    return render_to_response('create_theme.html', variables)

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

def update_team(request, team_id):
    team = get_object_or_404(Pack, id=team_id)
    form = TeamForm(request.POST, instance=team)
    if form.is_valid():
        form.save()
        return HttpResponseRedirect('/team')
    else:
        form = TeamForm(instance=team) 
        variables = RequestContext(request, {'form':form, 'team_id':team_id})
    
    return render_to_response('create_team.html', variables)

def delete_team(request, team_id):
    team = get_object_or_404(Team, id=team_id)
    team.delete()
    
    return HttpResponseRedirect('/team')

def index_team(request):
    teams = Team.objects.all()
    template = loader.get_template('index_team.html')
    context = RequestContext(request, {'teams':teams})
    return HttpResponse(template.render(context))