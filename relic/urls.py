from django.urls import path
from . import views


urlpatterns = [
    path('list/', views.save_list_data),
    # path('detail/', views.relic_detail),
]
