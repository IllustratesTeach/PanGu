package nirvana.hall.spark.services
import java.awt.{BorderLayout, Color, Graphics, GridLayout, Toolkit}
import java.io.{ByteArrayInputStream, File}
import javax.imageio.ImageIO
import javax.swing.{JFrame, JLabel, JPanel}

/**
  * Created by wangjue on 2017/1/10.
  */

object FPTView extends JFrame{
  val screen = Toolkit.getDefaultToolkit.getScreenSize
  val screenWidth = screen.getWidth.toInt
  val screenHeight = screen.getHeight.toInt
  var personId = ""
  var fgpCase = 0
  var fgp = 3
  var templates  = List[Map[Int,Array[Byte]]]()
  val cases = List[Map[Int,Array[Byte]]]()
  val frame = new JFrame("fpt image view")
  val container = frame.getContentPane
  val panel = new JPanel()
  def init(): Unit = {
    container.setBackground(Color.GRAY)
    panel.setLayout(new GridLayout(1,2))
    frame.setSize(screenWidth, screenHeight)
    frame.setVisible(true)
  }

  def parseFPT(fptPath : String, url : String) : Unit = {
    val(listTemplate,listCase) = new FPTParse().parse(fptPath,url)
    if (listTemplate.size>0) {
      var rollMap = Map[Int,Array[Byte]]()
      var flatMap = Map[Int,Array[Byte]]()
      listTemplate.foreach{ t=>
        if (t.fgpCase.toInt == 0) {//滚动指纹
          rollMap += (t.fgp.toInt -> t.fingerData)
        }
        t.fgpCase.toInt match {
          case 0 => rollMap += (t.fgp.toInt -> t.fingerData)
          case 1 => flatMap += (t.fgp.toInt -> t.fingerData)
          case _ => throw new IllegalArgumentException("Illegal fgpCase :"+t.fgpCase)
        }
      }
      templates = rollMap :: flatMap :: Nil
    }
  }

  def viewFPT(): Unit = {
    val roll = templates(fgpCase)
    val rollThumb = roll(fgp)
    val imageData = new ByteArrayInputStream(rollThumb)
    val image = ImageIO.read(imageData)

    init()

    /*val panelListInfo = new JPanel()
    panelListInfo.setBackground(Color.GREEN)
    panel.add(panelListInfo)*/

    val panelImg = new JPanel()
    {
      override def paint(g:Graphics)
      {
        super.paint(g)
        g.drawImage(image,0,0,1040,940,null)
      }
    }
    //panelImg.setBackground(Color.RED)
    panel.add(panelImg)
    container.add(panel,BorderLayout.CENTER)
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
    val url = "http://127.0.0.1:8001/extractor"
    val fptPath = "C:\\Users\\wangjue\\Desktop\\fail_FPT\\20170220\\R9999912016112221298178.FPT"
    //val fptPath = args(0)
    parseFPT(fptPath,url)
    viewFPT()
    //toolFPT()
  }
}