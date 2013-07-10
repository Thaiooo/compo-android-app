from django.conf.urls import patterns, url

from compo_manager import views
from compo_manager.forms import MatchFormPackStep1, MatchFormTeamsStep2,\
    MatchFormFieldsStep3

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

    #Theme manager
    url(r'^theme/create/$', views.create_theme, name="create-theme"),
    url(r'^theme/update/(?P<theme_id>\d+)/$', views.update_theme, name="update-theme"),
    url(r'^theme/delete/(?P<theme_id>\d+)/$', views.delete_theme, name="delete-theme"),
    url(r'^theme/$', views.index_theme, name="index-theme"),
    
    #Pack manager
    url(r'^pack/create/$', views.create_pack, name="create-pack"),
    url(r'^pack/update/(?P<pack_id>\d+)/$', views.update_pack, name="update-pack"),
    url(r'^pack/delete/(?P<pack_id>\d+)/$', views.delete_pack, name="delete-pack"),
    url(r'^pack/$', views.index_pack, name="index-pack"),
    
    #Team manager
    url(r'^team/create/$', views.create_team, name="create-team"),
    url(r'^team/update/(?P<team_id>\d+)/$', views.update_team, name="update-team"),
    url(r'^team/delete/(?P<team_id>\d+)/$', views.delete_team, name="delete-team"),
    url(r'^team/$', views.index_team, name="index-team"),
    
    #Match manager
    url(r'^match/create/$', views.MatchWizard.as_view([MatchFormPackStep1, MatchFormTeamsStep2, MatchFormFieldsStep3]), name="create-match"),
    url(r'^match/$', views.index_match, name="index-match"),
    
)
