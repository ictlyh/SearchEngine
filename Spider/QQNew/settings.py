# -*- coding: utf-8 -*-

# Scrapy settings for QQNew project
#
# For simplicity, this file contains only the most important settings by
# default. All the other settings are documented here:
#
#     http://doc.scrapy.org/en/latest/topics/settings.html
#

BOT_NAME = 'QQNew'

SPIDER_MODULES = ['QQNew.spiders']
NEWSPIDER_MODULE = 'QQNew.spiders'
ITEM_PIPELINES = {
	'QQNew.pipelines.QqnewPipeline' : 300
}

DOWNLOAD_DELAY = 0.25

# Crawl responsibly by identifying yourself (and your website) on the user-agent
#USER_AGENT = 'QQNew (+http://www.yourdomain.com)'
