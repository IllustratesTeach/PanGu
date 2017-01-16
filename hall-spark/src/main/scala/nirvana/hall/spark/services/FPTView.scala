package nirvana.hall.spark.services

import java.applet.Applet
import java.awt.{Color, Graphics}
import java.io.{ByteArrayInputStream, File}
import javax.imageio.ImageIO
import javax.swing.{JFrame, JLabel, JPanel}

/**
  * Created by wangjue on 2017/1/10.
  */
class FPTView{



  /*override def start(): Unit = {
    repaint()
  }

  override def init(): Unit = {
    //this.resize(640,640)
    val fptPath = "D:\\ftp\\cc\\R9999912016100712431151.fpt"
    view(fptPath)

    val frame = new JFrame
    frame.setSize(640,640)
    frame.setVisible(true)
    val g = frame.getGraphics
    val roll = templates(0)
    val rollThumb = roll(1)
    val imageData = new ByteArrayInputStream(rollThumb)
    val image = ImageIO.read(imageData)
    g.drawImage(image,0,0,640,640,null)
  }

  override def paint(g: Graphics): Unit = {
    val roll = templates(0)
    //val flat = templates(1)
    val rollThumb = roll(1)
    val imageData = new ByteArrayInputStream(rollThumb)
    val image = ImageIO.read(imageData)
    g.drawImage(image,0,0,640,640,null)
  }*/


}

object FPTView extends JFrame{

  var personId = ""
  var fgpCase = 0
  var fgp = 1
  var templates  = List[Map[Int,Array[Byte]]]()
  val cases = List[Map[Int,Array[Byte]]]()
  val frame = new JFrame("fpt image view")

  def init(): Unit = {
    frame.setSize(840, 840)
    frame.setVisible(true)
    frame.setBackground(Color.WHITE)
  }

  def parseFPT(fptPath : String) : Unit = {
    val(listTemplate,listCase) = new FPTParse().parse(fptPath)
    if (listTemplate.size>0) {
      listTemplate.foreach{ t=>
        var rollMap = Map[Int,Array[Byte]]()
        var flatMap = Map[Int,Array[Byte]]()
        if (t.fgpCase.toInt == 0) {//滚动指纹
          rollMap += (t.fgp.toInt -> t.fingerData)
        }
        t.fgpCase.toInt match {
          case 0 => rollMap += (t.fgp.toInt -> t.fingerData)
          case 1 => flatMap += (t.fgp.toInt -> t.fingerData)
          case _ => throw new IllegalArgumentException("Illegal fgpCase :"+t.fgpCase)
        }
        templates = rollMap :: flatMap :: Nil
      }
    }
  }

  def viewFPT(): Unit = {
    val roll = templates(fgpCase)
    val rollThumb = roll(fgp)
    val imageData = new ByteArrayInputStream(rollThumb)
    val image = ImageIO.read(imageData)

    init()

    val panelImg = new JPanel()
    {
      override def paint(g:Graphics)
      {
        super.paint(g)
        g.drawImage(image,0,0,640,640,null)
      }
    }
    panelImg.setSize(640,640)
    panelImg.setBounds(0,0,640,640)
    panelImg.setBackground(Color.white)
    frame.add(panelImg)
  }

  def toolFPT(): Unit = {
    init()
    val panelTool = new JPanel()
    panelTool.setSize(200,200)
    //panelTool.setBounds(640,640,200,200)
    panelTool.setLocation(700,700)
    Array("人员编号","指位类型","指位").foreach{ t=>
      val labelText = new JLabel(t)
      val labelItem = new JLabel("1111111")
      panelTool.add(labelText)
      panelTool.add(labelItem)
    }
    panelTool.setBackground(Color.CYAN)
    frame.add(panelTool)
  }


  def main(args : Array[String]) : Unit = {
    val fptPath = "D:\\ftp\\cc\\R9999912016100712431151.fpt"
    parseFPT(fptPath)
    viewFPT()
    toolFPT()
  }
}