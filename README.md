# LSPT-Crawling-Subsystem
Team Crawl So Hard

Interface methods (only one):

public Map<String, Object> **crawlUrl**(String url);
Method receiving the API caller input URL and constructing a Map<String, Object> that contains:

* A list of outgoing links and the HTTP status code for the given URL. The list will not contain any links using the "mailto" URI scheme, i.e. any links of the form "mailto:example@<span></span>example.com". 
* The list may contain "dead" links.
* **Parameter** “url": the URL that will be crawled for its HTML
* **Returns**: a map with two string keys: "statusCode" and "links". "statusCode" contains the integer status code returned by the GET request on that URL, and "links" contains a List of Strings that are the URLs of the outgoing links from the URL argument. In JSON this would be similar to: <br/>
&nbsp;&nbsp;&nbsp;&nbsp;{ "statusCode": 200, <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"links": [ "http://<span></span>outgoinglink1.com/", <br/>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"http://<span></span>outgoinglink2.com/", <br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"http://<span></span>possiblydeadlink.com/" <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;]<br/>
&nbsp;&nbsp;&nbsp;&nbsp;}
