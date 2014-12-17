# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html

import json

class QqnewPipeline(object):
	def __init__(self):
		self.file = open('QQNew.json', 'wb')

	def process_item(self, item, spider):
		if(item['title'] != "") :
			line = json.dumps(dict(item)) + ",\n"
			self.file.write(line.encode('utf-8').decode("unicode_escape"))
			return item

	def spider_closed(self, spider):
		self.file.close()
