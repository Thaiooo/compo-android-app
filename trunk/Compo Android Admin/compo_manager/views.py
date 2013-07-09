from django.http.response import HttpResponse, HttpResponseRedirect
from django.template import loader
from django.template.context import RequestContext
from compo_manager.models import ThemeForm, Theme, PackForm, Pack
from django.shortcuts import render_to_response, get_object_or_404

# View functions
def index(request):
    template = loader.get_template('index.html')
    context = RequestContext(request)
    return HttpResponse(template.render(context))

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