from django.db import models

# Create your models here.
class relic_list(models.Model):
    list_id = models.AutoField(primary_key=True)    # AutoField = auto_increment
    # id = models.AutoField(primary_key=True)    # AutoField = auto_increment
    ccma_name = models.TextField()
    ccba_mnm1 = models.TextField()
    ccba_mnm2 = models.TextField()
    ccba_kdcd = models.TextField()
    ccba_ctcd = models.TextField()
    ccba_asno = models.TextField()

# class relic_detail(models.Model):
    # relic_id = models.IntegerField(primary_key=True, unique=True)
    # list_id = models.ForeignKey()