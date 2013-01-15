package com.pidikiti.scalate

import java.util.Date
import org.fusesource.scalate.support.SiteGenerator

/**
 * User: ramesh
 * Date: 1/10/13
 * Time: 3:29 PM
 */

case class Document( val id:Long,
                     val title:String,
                     val url:String,
                     val description:String,
                     val publishedAt:Date = new Date,
                     val imageUrl:String,
                     val site:Site,
                     val author:String,
                     val concepts:List[Concept],
                     val topics:List[Topic],
                     val cleanText:String,
                     val licenseType:Int,
                     val imageHeight:Int,
                     val imageWidth:Int
                     )

case class Site(val id:Long, val name:String, val url:String, val logo:String)

case class Concept(val id:Long, val name:String, val weight:Double, val count:Int)

case class Topic(val id:Long, val name:String, val weight:Double, val count:Int)
