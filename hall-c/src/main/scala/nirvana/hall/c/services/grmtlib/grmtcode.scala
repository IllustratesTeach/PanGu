package nirvana.hall.c.services.grmtlib

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-24
 */
object grmtcode {

  final val FSSPORTDEF_SERVER = 6715		//front side server port

  //OP_CLASS
  final val OP_CLASS_RMTLIB = 120

  //OP_CLASS_RMTLIB

  //server
  final val OP_RMTLIB_SERVER_GETALL = 6000	//get all server
  final val OP_RMTLIB_SERVER_ADD = 6001	//add server
  final val OP_RMTLIB_SERVER_DEL = 6002	//delete server
  final val OP_RMTLIB_SERVER_UPDATE = 6003	//update server
  final val OP_RMTLIB_SERVER_GET = 6004	//get server by unitcode
  final val OP_RMTLIB_SERVER_GETBYSERVERNAME = 6005	//get server by server name
  final val OP_RMTLIB_SERVER_GETBYSERVERID = 6006	//get server by server id
  final val OP_RMTLIB_SERVER_GETUUIDBYNAME = 6007	//get server uuid by server name

  //router
  final val OP_RMTLIB_ROUTER_GETALL = 6100	//get all router table
  final val OP_RMTLIB_ROUTER_ADD = 6101	//add router
  final val OP_RMTLIB_ROUTER_DEL = 6102	//delete router by sid
  final val OP_RMTLIB_ROUTER_UPDATE = 6103	//update router
  final val OP_RMTLIB_ROUTER_GET = 6104	//get router by sid
  final val OP_RMTLIB_ROUTER_DELBYPROXYUNITCODE = 6105	//delete router by proxy unitcode

  //autotransmit config
  final val OP_RMTLIB_TRANSMITCFG_GETALL = 6200	//get all auto transmit config
  final val OP_RMTLIB_TRANSMITCFG_ADD = 6201	//add auto transmit config
  final val OP_RMTLIB_TRANSMITCFG_DEL = 6202	//delete auto transmit config
  final val OP_RMTLIB_TRANSMITCFG_UPDATE = 6203	//update auto transmit config
  final val OP_RMTLIB_TRANSMITCFG_GET = 6204	//get auto transmit config
  final val OP_RMTLIB_TRANSMITCFG_SAVEALL = 6205	//save all auto transmit config

  //transmit queue
  final val OP_RMTLIB_TRANSMITQUE_GET50 = 6301	//get transmit queue, compatible with gafis 5.0
  final val OP_RMTLIB_TRANSMITQUE_ADD50 = 6302	//add transmit queue, compatible with gafis 5.0
  final val OP_RMTLIB_TRANSMITQUE_DEL50 = 6303	//del transmit queue, compatible with gafis 5.0

  final val OP_RMTLIB_TRANSMITQUE_RETR = 6310	//retrive transmit queue by condtion
  final val OP_RMTLIB_TRANSMITQUE_GET = 6311	//get transmit queue
  final val OP_RMTLIB_TRANSMITQUE_ADD = 6312	//add transmit queue
  final val OP_RMTLIB_TRANSMITQUE_DEL = 6313	//del transmit queue
  final val OP_RMTLIB_TRANSMITQUE_UPDATE = 6314	//update transmit queue
  final val OP_RMTLIB_TRANSMITQUE_SETSTATUS = 6315	//set transmit queue status
  final val OP_RMTLIB_TRANSMITQUE_SETPLUSID = 6316	//set transmit queue plus id
  final val OP_RMTLIB_TRANSMITQUE_SETAFISERRNO = 6317	//set transmit queue afis error number
  final val OP_RMTLIB_TRANSMITQUE_UPDATESIMPQUE = 6318	//update simple transmit queue struct
  final val OP_RMTLIB_TRANSMITQUE_GETSIMPQUE = 6319	//get simple transmit queue struct

  //key queue
  final val OP_RMTLIB_KEYQUE_GET50 = 6401	//get key queue, compatible with gafis 5.0
  final val OP_RMTLIB_KEYQUE_ADD50 = 6402	//add key queue, compatible with gafis 5.0
  final val OP_RMTLIB_KEYQUE_DEL50 = 6403	//del key queue, compatible with gafis 5.0

  final val OP_RMTLIB_KEYQUE_RETR = 6410	//retrive key queue by condtion
  final val OP_RMTLIB_KEYQUE_GET = 6411	//get key queue
  final val OP_RMTLIB_KEYQUE_ADD = 6412	//add key queue
  final val OP_RMTLIB_KEYQUE_DEL = 6413	//del key queue
  final val OP_RMTLIB_KEYQUE_UPDATE = 6414	//update key queue
  final val OP_RMTLIB_KEYQUE_SETSTATUS = 6415	//set key queue status
  final val OP_RMTLIB_KEYQUE_SETPLUSID = 6416	//set key queue plus id
  final val OP_RMTLIB_KEYQUE_SETAFISERRNO = 6417	//set key queue afis error number

  //query and data control
  final val OP_RMTLIB_QRYANDDATACTRL_GETALL = 6500	//get all query control
  final val OP_RMTLIB_QRYANDDATACTRL_GET = 6501	//get query control by sid
  final val OP_RMTLIB_QRYANDDATACTRL_ADD = 6502	//add query control
  final val OP_RMTLIB_QRYANDDATACTRL_DEL = 6503	//del query control
  final val OP_RMTLIB_QRYANDDATACTRL_UPDATE = 6504	//update query control
  final val OP_RMTLIB_QRYANDDATACTRL_DELBYUSERNAME = 6505	//del query control by user name

  //used for gafis 5.0
  final val OP_RMTLIB_GETKEYLIST50 = 6600	//get key list by condition
  final val OP_RMTLIB_GETDEFDBID50 = 6601	//get default database id
  final val OP_RMTLIB_GETQRYSTATE50 = 6604	//get query status
  final val OP_RMTLIB_SETQRYSTATE50 = 6607	//set query status
  final val OP_RMTLIB_SETQRYRMTSTATE50 = 6608	//set query remote state
  final val OP_RMTLIB_GETQRYRMTSTATE50 = 6609	//get query remote state
  final val OP_RMTLIB_OVERWRITEKEY50 = 6610	//get overwrite flag
  final val OP_RMTLIB_GETDOWNLOADQUERY50 = 6611	//get download query
  final val OP_RMTLIB_GETQRYFLAGEX50 = 6612	//get query flagex
  final val OP_RMTLIB_SETQRYFLAGEX50 = 6613	//set query flagex
  final val OP_RMTLIB_RETRFINISEDHQUERY50 = 6614	//get all finished query id list
  final val OP_RMTLIB_RETRTPCARDTXT50 = 6615	//get all tpcard's text by condition
  final val OP_RMTLIB_RETRLPCASETXT50 = 6616	//get all lpcase's text by condition

  //query echo
  final val OP_RMTLIB_ECHO_GETALL = 6630	//get all query echo config
  final val OP_RMTLIB_ECHO_GET = 6631	//get query echo config
  final val OP_RMTLIB_ECHO_ADD = 6632	//add query echo config
  final val OP_RMTLIB_ECHO_DEL = 6633	//delete query echo config by sid
  final val OP_RMTLIB_ECHO_UPDATE = 6634	//update query echo config
  final val OP_RMTLIB_ECHO_DELBYUSERNAME = 6635	//delete query echo config by user name
  final val OP_RMTLIB_ECHO_SETLASTOPTIME = 6636	//set last operate time

  //auto query
  final val OP_RMTLIB_AUTOQRYCFG_GET50 = 6650	//get auto send query config, compatible with gafis 5.0

  final val OP_RMTLIB_AUTOQRYCFG_GET = 6660	//get auto send query config
  final val OP_RMTLIB_AUTOQRYCFG_DEL = 6661	//del auto send query config by sid
  final val OP_RMTLIB_AUTOQRYCFG_ADD = 6662	//add auto send query config
  final val OP_RMTLIB_AUTOQRYCFG_UPDATE = 6663	//update auto send query config
  final val OP_RMTLIB_AUTOQRYCFG_GETALL = 6664	//get all auto send query config
  final val OP_RMTLIB_AUTOQRYCFG_DELBYUSERNAME = 6665	//del auto send query config by username

  //transmit history
  final val OP_RMTLIB_TRANSMITHISTORY_DEL = 6670	//del transmit history
  final val OP_RMTLIB_TRANSMITHISTORY_ADD = 6671	//add transmit history
  final val OP_RMTLIB_TRANSMITHISTORY_RETR = 6672	//retive transmit history by condition

  //remote user database
  final val OP_RMTLIB_USERDB_GET = 6680	//get remote user database config
  final val OP_RMTLIB_USERDB_DEL = 6681	//del remote user database config by sid
  final val OP_RMTLIB_USERDB_ADD = 6682	//add remote user database config
  final val OP_RMTLIB_USERDB_UPDATE = 6683	//update remote user database config
  final val OP_RMTLIB_USERDB_GETALL = 6684	//get all remote user database config
  final val OP_RMTLIB_USERDB_DELBYUSERNAME = 6685	//del remote user database config by user name

  final val OP_RMTLIB_PARAM_GETVALUE = 6690	//get parameter's value in txserver.ini
  final val OP_RMTLIB_PARAM_SETVALUE = 6691	//set parameter's value in txserver.ini

  final val OP_RMTLIB_ASYNCMD_GET = 6700	//get asyn command
  final val OP_RMTLIB_ASYNCMD_ADD = 6701	//add asyn command
  final val OP_RMTLIB_ASYNCMD_DEL = 6702	//del asyn command
  final val OP_RMTLIB_ASYNCMD_UPDATE = 6703	//update asyn command
  final val OP_RMTLIB_ASYNCMD_UPDATESIMPCMD = 6704	//update simple asyn command
  final val OP_RMTLIB_ASYNCMD_SETSTATUS = 6705	//set asyn command's status

  final val OP_RMTLIB_DATCTRL_GET = 6710	//get data control
  final val OP_RMTLIB_DATCTRL_ADD = 6711	//add data control
  final val OP_RMTLIB_DATCTRL_DEL = 6712	//del data control by sid
  final val OP_RMTLIB_DATCTRL_UPDATE = 6713	//update data control
  final val OP_RMTLIB_DATCTRL_GETALL = 6714	//get all data control
  final val OP_RMTLIB_DATCTRL_DELBYUSERNAME = 6715	//del data control by username
  final val OP_RMTLIB_DATCTRL_CANUPDATE = 6716	//judge data can be updated

  //rmtlib other opcode
  final val OP_RMTLIB_TEST = 6800	//test tx server and db server is ok
  final val OP_RMTLIB_SENDCMD = 6801	//send command, RMTLIB_CMD_XXX
  final val OP_RMTLIB_TESTISVALID = 6802	//test tx server is ok
  final val OP_RMTLIB_GETDBSVRNAMEANDPORT = 6803	//get db server name and port
  final val OP_RMTLIB_GETMAINTXSVRNAMEANDPORT = 6804	//get main txserver name and port

  final val OP_RMTLIB_RETRKEYLIST = 6821	//retrive key list by condition
  final val OP_RMTLIB_RETRFINISHEDQRY = 6822	//retrive finished query sid list
  final val OP_RMTLIB_GETDOWNLOADQRY = 6823	//get download query
  final val OP_RMTLIB_EMPTYTABLE = 6824	//empty fixed table
  final val OP_RMTLIB_GETDEFDBID = 6825	//get default database id
  final val OP_RMTLIB_CHECKDATABASE = 6826	//check database
  final val OP_RMTLIB_GETQRYRMTSTATE = 6827	//get query remote state
  final val OP_RMTLIB_SETQRYRMTSTATE = 6828	//set query remote state
  final val OP_RMTLIB_GETQRYFLAGEX = 6829	//get query flagex
  final val OP_RMTLIB_SETQRYFLAGEX = 6830	//set query flagex
  final val OP_RMTLIB_RETRFINISHEDTRANSMITQUE = 6831	//retrive finished transmit queue
  final val OP_RMTLIB_RETRFINISHEDKEYQUE = 6832	//retrive finished key queue
  final val OP_RMTLIB_GETRMTSVRPARAM = 6833	//get remote server parameter
  final val OP_RMTLIB_SETRMTSVRPARAM = 6834	//set remote server parameter
  final val OP_RMTLIB_SETINT1VALUEBYCOLNAME = 6835	//set int1 value by column name
  final val OP_RMTLIB_GETINT1VALUEBYCOLNAME = 6836	//get int1 value by column name
  final val OP_RMTLIB_GETQRYSTATUS = 6837	//get query status
  final val OP_RMTLIB_SETQRYSTATUS = 6838	//set query status
  final val OP_RMTLIB_ISKEYEXIST = 6839	//judge key is exist?
  final val OP_RMTLIB_GETALLRMTUSERID = 6840	//get all remote user id
  final val OP_RMTLIB_CHECKRMTCONFIG = 6841	//check remote config
  final val OP_RMTLIB_CHECKRMTDB = 6842	//check remote database
  final val OP_RMTLIB_DELBYSID = 6843	//delete table by sid
  final val OP_RMTLIB_GETLOCUNITCODE = 6844	//get unitcode of local database server
  final val OP_RMTLIB_GETDBMAPTABLE = 6845	//get database map table
  final val OP_RMTLIB_SETDBMAPTABLE = 6846	//set database map table
  final val OP_RMTLIB_GETALLRMTUSERINFO = 6847	//get all remote user info
  final val OP_RMTLIB_GETUPDATEFLAG = 6848	//get update flag
  final val OP_RMTLIB_GETSVRVERSION = 6849	//get version of tx server
  final val OP_RMTLIB_GETIPFILTERS = 6850	//get ip filters
  final val OP_RMTLIB_SETIPFILTERS = 6851	//set ip filters
  final val OP_RMTLIB_GETERRLIST = 6852	//get all error on tx server
  final val OP_RMTLIB_GETRPTDATFILTERS = 6853	//get report data filters
  final val OP_RMTLIB_SETRPTDATFILTERS = 6854	//set report data filters
  final val OP_RMTLIB_GETALLSVRINFO = 6855	//get all svr info
  final val OP_RMTLIB_GETDATACTRLFLAG = 6856	//get data control flag, addnew. update or download
  final val OP_RMTLIB_GETIPMAPTABLE = 6857	//get ip map table
  final val OP_RMTLIB_SETIPMAPTABLE = 6858	//set ip map table
  final val OP_RMTLIB_GETREQUIREDTEXTITEM = 6859	//get required text item
  final val OP_RMTLIB_GETSVRVERSIONEX = 6860	//get tx and db server version
  final val OP_RMTLIB_GETPARAMLIST = 6861	//get param list
  final val OP_RMTLIB_GETDATACTRLFLAGEX = 6862	//get data control flag
  final val OP_RMTLIB_GETSYNCITEMS = 6863	//get sync items
  final val OP_RMTLIB_GETFATALERRLIST = 6864	//get fatal error list from direct upper server
  final val OP_RMTLIB_SETDOWNLOADQRYFLAG = 6865	//set download query flag

  final val OP_RMTLIB_LIVESCAN_PERSONUNAVAIL = 6880	//person has been released

  final val OP_RMTLIB_QUERY_ADD = 6900	//add query
  final val OP_RMTLIB_QUERY_UPDATE = 6901	//update query
  final val OP_RMTLIB_QUERY_DEL = 6902	//del query
  final val OP_RMTLIB_QUERY_RETR = 6903	//retrive query by condition
  final val OP_RMTLIB_QUERY_GET = 6904	//get query
  final val OP_RMTLIB_QUERY_GETESTIMATEFINTIME = 6905	//get estimate finished time
  final val OP_RMTLIB_QUERY_GETRESULT = 6906	// get query result

  // Beagle-add-start(2008-03-24): user-remote-config property
  // User remote config property
  final val OP_RMTLIB_USERREMOTECFG_ADD = 7000	// add remote config property
  final val OP_RMTLIB_USERREMOTECFG_UPDATE = 7001	// update remote config property
  final val OP_RMTLIB_USERREMOTECFG_DELETE = 7002	// delete remote config property
  final val OP_RMTLIB_USERREMOTECFG_GET = 7003	// get remote config property
  final val OP_RMTLIB_USERREMOTECFG_GETALL = 7004	// get all remote config property
  final val OP_RMTLIB_USERREMOTECFG_MULTISAVE = 7005	// save multi remote config property

  final val OP_RMTLIB_RMTUSERTRANSMITCFG_ADD = 7100	// add rmt User transmit config
  final val OP_RMTLIB_RMTUSERTRANSMITCFG_UPDATE = 7101	// update rmt User transmit config
  final val OP_RMTLIB_RMTUSERTRANSMITCFG_DELETE = 7102	// delete rmt User transmit config
  final val OP_RMTLIB_RMTUSERTRANSMITCFG_DELBYUSERNAME = 7103	// del rmt User transmit config by user name
  final val OP_RMTLIB_RMTUSERTRANSMITCFG_GET = 7104	// get rmt User transmit config
  final val OP_RMTLIB_RMTUSERTRANSMITCFG_GETALL = 7105	// get all User transmit config
  final val OP_RMTLIB_RMTUSERTRANSMITCFG_GETBYUSERNAME = 7106	// get User transmit config by user name
  final val OP_RMTLIB_RMTUSERTRANSMITCFG_MULTISAVE = 7107	// save multi rmt User transmit config

  // Beagle-add-end(2008-03-24)

  final val RMTLIB_CMD_TERMINATESVR = 0x0001		//terminate tx server
  final val RMTLIB_CMD_STOPSVR = 0x0002		//stop tx server
  final val RMTLIB_CMD_REFRESHSVR = 0x0003		//refresh tx server
  final val RMTLIB_CMD_SERVERCHANGED = 0x0004		//server changed
  final val RMTLIB_CMD_ROUTERCHANGED = 0x0005		//router changed
  final val RMTLIB_CMD_AUTOTRANSMITCFGCHANGED = 0x0006	//auto transmit config changed
  final val RMTLIB_CMD_REPORTQUECHANGED = 0x0007		//report queue changed
  final val RMTLIB_CMD_DOWNLOADQUECHANGED = 0x0008		//download queue changed
  final val RMTLIB_CMD_KEYQUECHANGED = 0x0009		//key queue changed
  final val RMTLIB_CMD_QYRCTRLCHANGED = 0x000A		//query control changed
  final val RMTLIB_CMD_TRANSMITQRYANDATACTRLCHANGED = 0x000B	//tansmit qry and data ctrl changed
  final val RMTLIB_CMD_QRYQUECHANGED = 0x000C			//query queue changed
  final val RMTLIB_CMD_AUTOQRYCHANGED = 0x000D			//auto query changed
  final val RMTLIB_CMD_ECHOCHANGED = 0x000E			//echo changed
  final val RMTLIB_CMD_RMTUSERDBCHANGED = 0x000F			//user database config changed
  final val RMTLIB_CMD_DBCHANGED = 0x0010			//db changed
  final val RMTLIB_CMD_PARAMCHANGED = 0x0011			//param changed
  final val RMTLIB_CMD_GETSERVERNAMEPROT = 0x0012			//get server name and port
  final val RMTLIB_CMD_LIVEUPDATE = 0x0013			//live update
  final val RMTLIB_CMD_DOWNLOADQUERY = 0x0014			//download query from direct server
  final val RMTLIB_CMD_GETMESSAGE = 0x0015			//get message from direct upper server
  final val RMTLIB_CMD_CHECKDATABASE = 0x0016			//check database
  final val RMTLIB_CMD_GETASYNCMD = 0x0017			//get asyn command
  final val RMTLIB_CMD_PROCESSASYNCMD = 0x0018			//process asyn command
  final val RMTLIB_CMD_GETDBMAPTABLE = 0x0019			//get database map table from first direct server
  final val RMTLIB_CMD_SENDASYNCMD = 0x001A			//send asyn command
  final val RMTLIB_CMD_RMTUSERCHANGED = 0x001B			//rmt user changed
  final val RMTLIB_CMD_IPFILTERSCHANGED = 0x001C			//ip filters changed
  final val RMTLIB_CMD_EMPTYERRLIST = 0x001D			//empty simp error list
  final val RMTLIB_CMD_RPTDATFILTERSCHANGED = 0x001E			//report data filters changed
  final val RMTLIB_CMD_DATCTRLCHANGED = 0x001F			//data control changed
  final val RMTLIB_CMD_REFRESHLIVESCANUSEDTHREAD = 0x0020	//refresh live scan used thread
  final val RMTLIB_CMD_IPMAPTABLECHANGED = 0x0021			//refresh ip map table
  final val RMTLIB_CMD_REFRESHFPXRPTTPLP = 0x0022			//refresh fpx report tplp thread
  final val RMTLIB_CMD_REFRESHFPXRPTQUERY = 0x0023			//refresh fpx report query
  final val RMTLIB_CMD_REFRESHINFOSYNCTHREAD = 0x0024		//refresh info sync thread
  final val RMTLIB_CMD_REFRESHCANDFILTER = 0x0025			//refresh cand filter
  final val RMTLIB_CMD_DOWNLOADQRYRESULT = 0x0026			// download query result from direct server

  final val RMTLIB_CMD_LIVESCANQRYQUECHANGED = 0x0027			//query queue changed

  final val RMTLIB_CMD_GETSVRINFO = 0x00FF			//get all server info



}
