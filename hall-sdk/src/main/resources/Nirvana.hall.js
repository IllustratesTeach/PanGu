/*
 * Copyright 2015 Jun Tsai.
 */
 /**
  * nirvana hall interface
  **/
(function(global) {
    "use strict";

    var ProtoBuf = dcodeIO.ProtoBuf;
    var HallAPI = {version:1,token:""};
    /**
     * 初始化API
     * @param url API 的URL地址
     */
    HallAPI.Init = function(url){
        this.url = url;
        this.builder = ProtoBuf.newBuilder({ convertFieldsToCamelCase: true });
        //添加公共类
        hall.api.AddProto("/sys/Common.json");
        //hall.api.AddProto("/sys/Model.json");

    };
    /**
     * 增加一个协议定义，目前尚不能增加多个协议.
     * @see https://github.com/dcodeIO/ProtoBuf.js/issues/289
     * TODO 能够支持添加多个协议
     * @param path
     * @constructor
     */
    HallAPI.AddProto=function(path){
        ProtoBuf.loadJsonFile(this.url+"/proto"+path,this.builder);
    };
    /**
     * 构建
     * @returns 命名空间
     */
    HallAPI.Build = function(){
        if(this.root){
            throw Error("Hall Protocol Built!");
        }else{
            this.root = this.builder.build();
            return this.root;
        }
    };

    /**
     * 执行某一个API
     * @param cmd API的命令
     * @param protobufRequest protobuf请求对象
     * @param success 当执行成功后的操作
     * @param fail 当执行失败后的操作
     */
    HallAPI.Execute= function(protobufRequest,success,fail){
        if(this.url == null){
            throw Error("URL Not Configuration");
        }
        var me = this;
        var xhr = ProtoBuf.Util.XHR();
        //var xhr = new plus.net.XMLHttpRequest();
        xhr.timeout = 60 * 1000;
        console.log("starting to fetch");

        xhr.open("POST",this.url,true);
        xhr.responseType = "arraybuffer";

        xhr.setRequestHeader('Content-Type','binary/octet-stream');
        xhr.setRequestHeader("X-Hall-Request","protobuf");
        xhr.onload = function(evt) {
            var error = xhr.getResponseHeader("X-Hall-Error")
            var responseType = me.root.sys.BaseResponse
            var response = responseType.decode(xhr.response);
            me.token = xhr.getResponseHeader("Hall-Token")
            if(error){
                if(fail)
                  fail(response.getMessage())
                else
                  alert(response.getMessage())
            }else{
                var responseCmd = protobufRequest.toString().replace(/Request$/,"Response")+".cmd";
                var protobufResponse = response.get(responseCmd)
                success(protobufResponse)
            };
            if(me.waiting){
                me.waiting.close();
            }
        };
        xhr.onerror = function(evt){
            fail("exception occur")
            if(me.waiting){
                me.waiting.close();
            }
        }
        var baseRequest= new this.root.sys.BaseRequest()
        baseRequest.setToken(this.token);
        baseRequest.setVersion(this.version);
        baseRequest.set(protobufRequest.toString()+".cmd",protobufRequest)

        xhr.send(baseRequest.toArrayBuffer());
    };

    (global["hall"] = global["hall"] || {})["api"] = HallAPI;
})(this);
