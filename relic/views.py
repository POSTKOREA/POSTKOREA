from django.shortcuts import render
from .models import relic_list
from rest_framework.decorators import api_view
import requests
from xml.etree import ElementTree
from django.http import HttpResponse

# Create your views here.
@api_view(["GET"])
def save_list_data(request):
    # 총 개수 15323
    total_page = 154
    page_unit = 100
    # 1페이지에 100개씩

    for idx in range(1, total_page + 1):
        url = f'http://www.cha.go.kr/cha/SearchKindOpenapiList.do?&ccbaCncl=N&pageUnit={page_unit}&pageIndex={idx}'
        response = requests.get(url)

        xml_data = ElementTree.fromstring(response.content)

        # Result > Item > 안에 있는 데이터들 for문으로 순차적으로 저장
        for item in xml_data.findall('item'):
            ccma_name = item.find('ccmaName').text
            ccba_mnm1 = item.find('ccbaMnm1').text
            # ccba_mnm2 = item.find('ccbaMnm2').text
            ccba_kdcd = item.find('ccbaKdcd').text
            ccba_ctcd = item.find('ccbaCtcd').text
            ccba_asno = item.find('ccbaAsno').text

            relic_list.objects.create(
                ccma_name=ccma_name,
                ccba_mnm1=ccba_mnm1,
                # ccba_mnm2=ccba_mnm2,
                ccba_kdcd=ccba_kdcd,
                ccba_ctcd=ccba_ctcd,
                ccba_asno=ccba_asno
            )
        # print(f'{idx} page succeeded')
        print(ccma_name, ccba_mnm1, ccba_kdcd, ccba_ctcd, ccba_asno)
    return HttpResponse("saved successfully")


# @api_view(["GET"])
# def relic_detail(request):
#     pass