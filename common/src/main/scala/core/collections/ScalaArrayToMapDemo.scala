package util.collections

/**
  * Created by yidxue on 2018/5/3
  */
object ScalaArrayToMapDemo {

  val mapping: String =
    """|4801,DNAME
       |5101,acwd
       |5106,avwd
       |4901,ixwd
       |5201,akwd
       |4701,esnatech
       |5006,abwd
       |5301,bvwd
       |5401,dd1wd
       |5501,apwd
       |5601,bpwd
       |1,dawd
       |101,dbwd
       |501,dewd
       |306,dcwd
       |401,ddwd
       |601,dhwd
       |701,dgwd
       |801,diwd
       |901,djwd
       |1001,dkwd
       |1101,dlwd
       |1201,dmwd
       |1301,dnwd
       |1401,iawd
       |1501,dowd
       |1601,dpwd
       |1701,dqwd-obsolete
       |1801,dtwd
       |1901,duwd
       |2001,dswd
       |2101,dvwd
       |2201,drwd
       |2301,dywd
       |2401,virtual
       |2501,delete me
       |2601,imwd
       |2701,dzwd
       |2801,ibwd
       |2901,dwwd
       |3001,iewd
       |3101,deleteme
       |3201,igwd
       |4001,iowd
       |3601,ijwd
       |-988811,testise
       |4401,ipwd
       |4601,eiwmd
       |3501,ihwd
       |4606,iwwd
       |3801,inwd
       |3401,ikwd
       |3906,blablaba
       |3701,ilwd
       |3901,gggwd
       |4501,iqwd
       |3301,idwd
       |4301,iuwd
       |5001,aawd
       |5701,bkwd
       |7701,svwd
       |7801,stwd
       |5801,adwd
       |5901,rtwd
       |6501,axwd
       |6101,atwd
       |6701,awwd
       |6801,aywd
       |6901,biwd
       |7001,cuwd
       |7101,cvwd
       |7201,evwd
       |7601,jtwd
       |6001,aiwd
       |6201,ctwd
       |6401,auwd
       |6406,buwd
       |6601,ahwd
       |7401,gtwd
       |7501,htwd
       |5906,arwd
       |6301,domaint
       |6706,etwd
       |7301,ctwd-obsolete
       |7901,ftwd
       |8001,ztwd
       |10607,muwd
       |9802,sywd
       |8401,aowd
       |8101,mvwd
       |9602,aswd-deactivate2
       |10007,dfwd
       |9502,aswd-deactivate
       |8201,nvwd
       |9702,aswd
       |10302,ltwd
       |10002,afwd
       |10102,vvwd
       |9902,mmwd
       |8301,ktwd
       |10202,rvwd""".stripMargin

  def manOf[T: Manifest](t: T): Manifest[T] = manifest[T]

  def main(args: Array[String]): Unit = {
    val list = mapping.split("\\n").map(x => x.split(",")).map(arr => arr(0) -> arr(1))

    println(manOf(list))
    list.foreach(println(_))
  }
}
