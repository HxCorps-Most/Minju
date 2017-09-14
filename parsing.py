
import urllib.request
from bs4 import  BeautifulSoup

#get html code from url

soup = BeautifulSoup(urllib.request.urlopen("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=%EA%B0%95%EB%AF%BC%EC%A3%BC"
), 'html5lib')

#parsing related keywords
related_keywords_soup = soup.find(id="nx_related_keywords").find('dd')
related_keywords_list = related_keywords_soup.find_all('a')
related_keywords = []
for list in related_keywords_list :
    keyword = list.string
    related_keywords.append(keyword)

#parsing news topic

news_topic_soup1 = soup.find(id="nxfr_htp").div
news_topic_soup2 = news_topic_soup1.next_sibling.next_sibling
news_topic_soup3 = news_topic_soup2.div.next_sibling.next_sibling
news_topic_list1 = news_topic_soup3.find_all("span", class_="tit")
news_topic_list2 = news_topic_soup3.next_sibling.next_sibling.find_all("span", class_="tit")
news_topic_news =  []
news_topic_entertain = []

for list in news_topic_list1 :
    keyword = list.string
    news_topic_news.append(keyword)

for list in news_topic_list2 :
    keyword = list.string
    news_topic_entertain.append(keyword)

#parsing target

target_soup = soup.find(id="nxfr_ugrank").div.next_sibling.next_sibling
target = soup.find(id="nxfr_ugrank").div.h2.string
target_list = target_soup.find_all("span",class_="tit")
target_favor = []

for list in target_list :
    keyword = list.string
    target_favor.append(keyword)


print("연관검색어 :", related_keywords)
print("뉴스토픽 뉴스 :", news_topic_news)
print("뉴스토픽 연예,스포츠 :", news_topic_entertain)
print(target ,":", target_favor)

