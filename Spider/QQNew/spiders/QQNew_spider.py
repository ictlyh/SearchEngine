from scrapy.spider import Spider
from scrapy.selector import Selector

from QQNew.items import QQNewItem
import random

import scrapy
from scrapy.contrib.spiders import CrawlSpider, Rule
from scrapy.contrib.linkextractors import LinkExtractor

import sys
reload(sys)
sys.setdefaultencoding('utf-8')

import os
import re
import string

import urllib2

class QQNewSpider(Spider) :
	name = "QQNew"
	allowed_domains = ["sports.qq.com"]
	start_urls = ["http://sports.qq.com"]
	
	
	def parse(self, response):
		sel = Selector(response)
		
		#Another Index Href
		
		yield scrapy.Request("http://sports.qq.com/nba/", callback = self.parseNBA)#NBA
		yield scrapy.Request("http://sports.qq.com/cba/", callback = self.parseCBA)#CBA
		yield scrapy.Request("http://sports.qq.com/csocce/csl/", callback = self.parseCSL)#ZHONGCHAO
		yield scrapy.Request("http://sports.qq.com/ucl/", callback = self.parseUCL)#OUGUAN
		yield scrapy.Request("http://sports.qq.com/isocce/Spain.htm", callback = self.parseSpain)#XIJIA
		yield scrapy.Request("http://sports.qq.com/isocce/England.htm", callback = self.parseENG)#YINGCHAO
		yield scrapy.Request("http://sports.qq.com/isocce/Italy.htm", callback = self.parseENG)#YIJIA
		yield scrapy.Request("http://sports.qq.com/bundesliga/", callback = self.parseGER)#DEJIA
		yield scrapy.Request("http://sports.qq.com/others/", callback = self.parseOther)#ZONGHE
		
		#Index Page Href
		
		for url in sel.xpath('//div/h2[@class = "yh f26"]/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
		for url in sel.xpath('//div[@class = "news_txt f14 l28"]/p/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
		for url in sel.xpath('//div[@class = "Q-tpList news_item"]/h3[@class = "yh f18"]/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
		
		
	def parsePage(self, response):
		sel = Selector(response)
		
		item = QQNewItem()
		
		item['url'] = response.url
		
		item['title'] = ""
		for title in sel.xpath('//div[@class = "head clearfix"]/div[@class = "title"]/h1/text()').extract() :
			item['title'] += title
		for title in sel.xpath('//div[@id = "C-Main-Article-QQ"]/div[@class = "hd"]/h1/text()').extract() :
			item['title'] += title
		for title in sel.xpath('//div[@id = "cntMain"]/div[@id = "cntL"]/div[@id = "ArticleTit"]/text()').extract() : #2009
			item['title'] += title
		
		item['content'] = ""
		for content in sel.xpath('//div[@id = "Cnt-Main-Article-QQ"]/p/text()').extract() :
			item['content'] += content
			
		for content in sel.xpath('//div[@id = "ArticleCnt"]/p/text()').extract() : #2009
			item['content'] += content
		
		try:
			jsStr = ""
			
			for scriptCode in sel.xpath('//body/div/div/div/div[@class = "main"]/script').extract() :
				jsStr += scriptCode
			for scriptCode in sel.xpath('//div[@class = "article-foot"]/script').extract() :
				jsStr += scriptCode
			cmtIdPattern = re.compile(r'cmt_id\s*=\s*(\d+)')
			cmtIdSearch = cmtIdPattern.search(jsStr)
			cmtId = cmtIdSearch.group(1)
			
			cmtIdUrl = 'http://coral.qq.com/article/' + cmtId + '/commentnum?callback=_cbSum&source=1&t=' + str(131072)
			
			commentJson = urllib2.urlopen(cmtIdUrl).read()
			cmtNumPattern = re.compile(r'"commentnum":"(\d+)"')
			cmtNumSearch = cmtNumPattern.search(commentJson)
		
			item['comment'] = cmtNumSearch.group(1)
		except:
			item['comment'] = 0
		
		yield item
		
		#News Page Href
		
		for url in sel.xpath('//div[@class = "bd"]/ul[@id = "hotReadList"]/li/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
		for url in sel.xpath('//div[@class = "bd"]/div[@class = "about-read"]/ul/li/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
		
	def parseNBA(self, response):
		sel = Selector(response)
		
		for url in sel.xpath('//div[@class = "topArea"]/div[@class = "topTxt"]/div[@class = "toutiaoBox"]/h2/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
		for url in sel.xpath('//div[@class = "topArea"]/div[@class = "topTxt"]/div[@class = "toutiaoBox"]/p/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
		for url in sel.xpath('//div[@class = "bd"]/div[@class = "teamR"]/ul/li/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
	def parseCBA(self, response):
		sel = Selector(response)
		
		urlPre = "http://sports.qq.com/"
		
		for url in sel.xpath('//div[@class = "newsBox"]/div[@class = "newsR"]/div[@class = "tiaotouBox"]/ul/li/a/@href').extract() :
			yield scrapy.Request(urlPre + url, callback = self.parsePage)
			
		for url in sel.xpath('//div[@class = "newsList"]/div/div[@class = "hd"]/h2/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parseCBASub)
			
	def parseCBASub(self, response):
		sel = Selector(response)
		
		for url in sel.xpath('//ul[@class = "list01 font_s_14 line_h_25"]/li/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
		
		pages = sel.xpath('//div[@class = "pageNav"]/a/text()')[-2].extract()
		totalPages = string.atoi(pages)
		
		urlPre = response.url[:-4]
		
		for page in range(2, totalPages + 1) :
			yield scrapy.Request(urlPre + "_" + str(page) + ".htm", self.parseCBAArea)
		
		
	def parseCBAArea(self, response) :
		sel = Selector(response)
		
		for url in sel.xpath('//ul[@class = "list01 font_s_14 line_h_25"]/li/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
	def parseCSL(self, response) :
		sel = Selector(response)
		
		for url in sel.xpath('//div[@class = "main"]/div[@class = "cl"]/div[@class = "top_news_20130801"]/h3/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
		for url in sel.xpath('//div[@class = "main"]/div[@class = "cl"]/div[@class = "top_news_20130801"]/ul/li/p/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
		for url in sel.xpath('//div[@class = "conBox"]/div[@class = "teamBox"]/div[@class = "hd"]/span/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parseCSLSub)
			
	def parseCSLSub(self, response) :
		sel = Selector(response)
		
		for url in sel.xpath('//div[@class = "mod newslist"]/ul/li/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
		
		pages = sel.xpath('//div[@class = "pageNav"]/a/text()')[-2].extract()
		totalPages = string.atoi(pages)
		
		urlPre = response.url[:-4]
		
		for page in range(2, totalPages + 1) :
			yield scrapy.Request(urlPre + "_" + str(page) + ".htm", self.parseCSLArea)
			
	def parseCSLArea(self, response) :
		sel = Selector(response)
		
		for url in sel.xpath('//div[@class = "mod newslist"]/ul/li/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
	def parseUCL(self, response) :
		sel = Selector(response)
		
		urlPre = "http://sports.qq.com/"
		
		for url in sel.xpath('//div[@class = "w1000"]/div[@class = "right"]/div/h2/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
		for url in sel.xpath('//div[@class = "w1000"]/div[@class = "right"]/ul/li/a/@href').extract() :
			yield scrapy.Request(urlPre + url, callback = self.parsePage)
			
		for url in sel.xpath('//div[@class = "newsListBox"]/div[@class = "xmNews"]/ul/li/a/@href').extract() :
			yield scrapy.Request(urlPre + url, callback = self.parsePage)
			
	def parseSpain(self, response) :
		sel = Selector(response)
		
		urlPre = "http://sports.qq.com/"
		
		for url in sel.xpath('//div[@class = "main"]/div[@class = "right"]/div/h2/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
		for url in sel.xpath('//div[@class = "main"]/div[@class = "right"]/div/ul/li/a/@href').extract() :
			yield scrapy.Request(urlPre + url, callback = self.parsePage)
			
		for url in sel.xpath('//div[@class = "newsListBox"]/div[@class = "xmNews"]/div[@class = "tit"]/span/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parseSpainSub)
			
	def parseSpainSub(self, response) :
		sel = Selector(response)
		
		for url in sel.xpath('//div[@class = "mod newslist"]/ul/li/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
		
		pages = sel.xpath('//div[@class = "pageNav"]/a/text()')[-2].extract()
		totalPages = string.atoi(pages)
		
		urlPre = response.url[:-4]
		
		for page in range(2, totalPages + 1) :
			yield scrapy.Request(urlPre + "_" + str(page) + ".htm", self.parseSpainArea)
			
	def parseSpainArea(self, response) :
		sel = Selector(response)
		
		for url in sel.xpath('//div[@class = "mod newslist"]/ul/li/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
	def parseENG(self, response) :
		sel = Selector(response)
		
		urlPre = "http://sports.qq.com/"
		
		for url in sel.xpath('//div[@class = "top_news_20130801"]/h3/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
		for url in sel.xpath('//div[@class = "top_news_20130801"]/ul/li/p/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
		for url in sel.xpath('//div/div[@class = "hd"]/ul/li[@class = "gengduo"]/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parseENGSub)
			
	def parseENGSub(self, response) :
		sel = Selector(response)
		
		for url in sel.xpath('//div[@class = "mod newslist"]/ul/li/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
		
		pages = sel.xpath('//div[@class = "pageNav"]/a/text()')[-2].extract()
		totalPages = string.atoi(pages)
		
		urlPre = response.url[:-4]
		
		for page in range(2, totalPages + 1) :
			yield scrapy.Request(urlPre + "_" + str(page) + ".htm", self.parseENGArea)
	def parseENGArea(self, response) :
		sel = Selector(response)
		
		for url in sel.xpath('//div[@class = "mod newslist"]/ul/li/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
	def parseGER(self, response) :
		sel = Selector(response)
		
		urlPre = "http://sports.qq.com/"
		
		for url in sel.xpath('//div[@class = "top_news_20130801"]/ul/h3/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
		for url in sel.xpath('//div[@class = "top_news_20130801"]/ul/li/p/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
		for url in sel.xpath('//div/div[@class = "bd"]/div[@class = "hot_list"]/ul/li/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
			
		for url in sel.xpath('//div/div[@class = "hd"]/ul/li[@class = "gengduo"]/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parseGERSub)
			
	def parseGERSub(self, response) :
		sel = Selector(response)
		
		for url in sel.xpath('//div[@class = "mod newslist"]/ul/li/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)
		
		pages = sel.xpath('//div[@class = "pageNav"]/a/text()')[-2].extract()
		totalPages = string.atoi(pages)
		
		urlPre = response.url[:-4]
		
		for page in range(2, totalPages + 1) :
			yield scrapy.Request(urlPre + "_" + str(page) + ".htm", self.parseGERArea)
	def parseGERArea(self, response) :
		sel = Selector(response)
		
		for url in sel.xpath('//div[@class = "mod newslist"]/ul/li/a/@href').extract() :
			yield scrapy.Request(url, callback = self.parsePage)