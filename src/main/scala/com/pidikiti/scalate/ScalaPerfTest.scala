package com.pidikiti.scalate

import org.fusesource.scalate.{TemplateSource, TemplateEngine}
import java.util.Date
import util.Random
import java.util.concurrent.Executors
import akka.dispatch.{Await, Future, ExecutionContext}
import akka.util.duration._

/**
 * User: ramesh
 * Date: 1/10/13
 * Time: 2:21 PM
 */

object ScalatePerfTest {

  var testData:scala.collection.mutable.ListBuffer[Document] = new scala.collection.mutable.ListBuffer[Document]();

  def main(args:Array[String])= {

    val totalThreads = args(0).toInt
    val totalDocs = args(1).toInt
    val howmanyTimes = args(2).toInt

    println("Initializing the test system")
    val execService = Executors.newFixedThreadPool(totalThreads)
    implicit val execContext = ExecutionContext.fromExecutorService(execService)

    generateTestData();
    val te = new TemplateEngine
    val ts = TemplateSource.fromFile("/Users/ramesh/dev/scalate-benchmarks/src/main/resources/html_output.mustache")
    val output = te.layout(ts, Map("doc" -> testData(Random.nextInt(99))))
    println(output)
    println("Starting the performance test of 10K docs with 20 threads")
    (1 to howmanyTimes).foreach( i => {
      val startTime = System.currentTimeMillis()
      val futures = (1 to totalDocs).map(i => Future(te.layout(ts, Map("doc" -> testData(Random.nextInt(99))))))
      val totalCompletedTx = Future.fold(futures)(0){(acc, x) => acc+1}
      val result = Await.result(totalCompletedTx, 100.minutes)
      val tt:Double = System.currentTimeMillis() - startTime
      println(" Total of " + result + " docs transformed in  " + tt + " milliseconds with an average rate of" + tt/totalDocs + " ms/doc")
    })
  }

  private def generateTestData() {
    (1 to 100).foreach(i => {
      testData += new Document( id=i, title="Reasonable size title, this seems to be ok, but lets add few more charatcers and see to make even better",
       url = "http://www.otherstuffyoulikeabout.com/bleedingedge/salamander/",
       description= "Reasonable size title, this seems to be ok, but lets add few more charatcers and see to make even better Reasonable size title, this seems to be ok, but lets add few more charatcers and see to make even betterReasonable size title, this seems to be ok, but lets add few more charatcers and see to make even betterReasonable size title, this seems to be ok, but lets add few more charatcers and see to make even better",
       publishedAt = new Date,
       imageUrl = "http://www.otherstuffyoulikeabout.com/bleedingedge/salamander/",
       site = new Site(id=1, name="dress a day, ur referecne to cool looking dresses", url="http://dressaday.com", logo="http://dressaday.com/images/testimage.jpg"),
       author = "davido robert james bond",
       concepts = getRandomConcepts,
       topics = getRandomTopics,
       cleanText = getRandomBody,
       licenseType = 2,
       imageHeight = 100,
        imageWidth = 300
      )
    })
    println("Total test data docs " + testData.size)
  }

  private def getRandomBody():String = {
    return (1 to 100).foldLeft("")((result,loopvalue) => result+" The difference happens because filter is immediately applied with List, returning a list of odds -- since found is false. Only then foreach is executed, but, by this time, changing found is meaningless, as filter has already executed")
  }

  private def getRandomConcepts():List[Concept] =  (1 to 10).map(i => new Concept(id=i, name = "Concept with test name "+i, weight= 0.5, count = 2)).toList

  private def getRandomTopics():List[Topic] =  (1 to 10).map(i => new Topic(id =i, name = "Topic with test name "+i, weight= 0.5, count = 2)).toList

}
