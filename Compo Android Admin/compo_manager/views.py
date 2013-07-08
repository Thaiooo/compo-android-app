from django.http.response import HttpResponse
from django.template import loader
from django.template.context import RequestContext
from compo_manager.models import ThemeForm, Theme
from django.shortcuts import render_to_response

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
    else:
        form = ThemeForm() 
    variables = RequestContext(request, {'form':form})
    return render_to_response('create_theme.html', variables)

def index_theme(request):
    themes = Theme.objects.all()
    template = loader.get_template('index_theme.html')
    context = RequestContext(request, {'themes':themes})
    return HttpResponse(template.render(context))