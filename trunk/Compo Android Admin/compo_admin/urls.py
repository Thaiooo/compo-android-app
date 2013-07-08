from django.conf.urls import patterns, url

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

    #Theme manager
    url(r'^theme/create/$', views.create_theme, name="create-theme"),
    url(r'^theme/$', views.index_theme, name="index-theme"),
    
)
