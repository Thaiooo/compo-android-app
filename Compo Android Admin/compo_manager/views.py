from django.http.response import HttpResponse
from compo_manager.models import Quizz
from django.template import loader
from django.template.context import Context


def index(request):
    quizz_list = Quizz.objects.order_by('update_time')
    template = loader.get_template('index.html')
    context = Context({'quizz_list':quizz_list,})
    
    return HttpResponse(template.render(context))

def lineup(request):
    
    return HttpResponse('lineup')