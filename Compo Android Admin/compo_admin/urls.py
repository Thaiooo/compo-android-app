from django.conf.urls import patterns, include, url

from compo_manager import views

# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'compo_admin.views.home', name='home'),
    # url(r'^compo_admin/', include('compo_admin.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    # url(r'^admin/', include(admin.site.urls)),
    
    url(r'^$', views.index, name='index'),
    url(r'^(?P<quizz_id>\d+)/$', views.lineup, name='lineup')
)
