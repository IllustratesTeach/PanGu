-- 捺印同步队列
-- Create table
create table HALL_NORMALTP_TPCARDINFO
(
  SID VARCHAR2(32) not null,
  SEQ NUMBER
)
tablespace TPLIB_DATTS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table HALL_NORMALTP_TPCARDINFO
  is '捺印同步队列表';
-- Add comments to the columns
comment on column HALL_NORMALTP_TPCARDINFO.SID
  is '捺印卡号';
comment on column HALL_NORMALTP_TPCARDINFO.SEQ
  is '序列HALL_NORMALTP_TPCARDINFO_SEQ';
-- Create/Recreate primary, unique and foreign key constraints
alter table HALL_NORMALTP_TPCARDINFO
  add constraint PK_HALL_NORMALTP_TPCARDINFO primary key (SID);

-- 创建捺印同步序列
create sequence HALL_NORMALTP_TPCARDINFO_SEQ
minvalue 1
maxvalue 99999999999999999999
start with 1
increment by 1
cache 20;


-- 创建捺印同步触发器
CREATE OR REPLACE TRIGGER HALL_NORMALTP_TPCARDINFO
  AFTER
  INSERT
  OR UPDATE
  OR DELETE ON AFIS.NORMALTP_TPCARDINFO FOR EACH ROW
DECLARE cardid VARCHAR2(32);
BEGIN
  IF :OLD.cardid IS NULL THEN
    cardid := :NEW.cardid;
  ELSE
    cardid := :OLD.cardid;
  END IF;
  DELETE FROM HALL_NORMALTP_TPCARDINFO WHERE HALL_NORMALTP_TPCARDINFO.sid = cardid;
  INSERT INTO HALL_NORMALTP_TPCARDINFO VALUES(cardid, HALL_NORMALTP_TPCARDINFO_SEQ.nextval);
END;

-- 现场指纹同步队列表
create table HALL_NORMALLP_LATFINGER
(
  SID VARCHAR2(32) not null,
  SEQ NUMBER
)
tablespace LPLIB_DATTS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table HALL_NORMALLP_LATFINGER
  is '现场指纹同步队列表';
-- Add comments to the columns
comment on column HALL_NORMALLP_LATFINGER.SID
  is '现场指纹卡号';
comment on column HALL_NORMALLP_LATFINGER.SEQ
  is '序列HALL_NORMALLP_LATFINGER_SEQ';
-- Create/Recreate primary, unique and foreign key constraints
alter table HALL_NORMALLP_LATFINGER
  add constraint PK_HALL_NORMALLP_LATFINGER primary key (SID);

--现场指纹同步序列
create sequence HALL_NORMALLP_LATFINGER_SEQ
minvalue 1
maxvalue 99999999999999999999
start with 1
increment by 1
cache 20;

CREATE OR REPLACE TRIGGER HALL_NORMALLP_LATFINGER
  AFTER
  INSERT
  OR UPDATE
  OR DELETE ON AFIS.NORMALLP_LATFINGER FOR EACH ROW
DECLARE fingerid VARCHAR2(32);
BEGIN
  IF :OLD.fingerid IS NULL THEN
    fingerid := :NEW.fingerid;
  ELSE
    fingerid := :OLD.fingerid;
  END IF;
  DELETE FROM HALL_NORMALLP_LATFINGER WHERE HALL_NORMALLP_LATFINGER.sid = fingerid;
  INSERT INTO HALL_NORMALLP_LATFINGER VALUES(fingerid, HALL_NORMALLP_LATFINGER_SEQ.nextval);
END;
\
-- 现场掌纹同步队列表
create table HALL_NORMALLP_LATPALM
(
  SID VARCHAR2(32) not null,
  SEQ NUMBER
)
tablespace LPLIB_DATTS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table HALL_NORMALLP_LATPALM
  is '现场掌纹同步队列表';
-- Add comments to the columns
comment on column HALL_NORMALLP_LATPALM.SID
  is '现场掌纹卡号';
comment on column HALL_NORMALLP_LATPALM.SEQ
  is '序列HALL_NORMALLP_LATPALM_SEQ';
-- Create/Recreate primary, unique and foreign key constraints
alter table HALL_NORMALLP_LATPALM
  add constraint PK_HALL_NORMALLP_LATPALM primary key (SID);

--现场掌纹同步序列
create sequence HALL_NORMALLP_LATPALM_SEQ
minvalue 1
maxvalue 99999999999999999999
start with 1
increment by 1
cache 20;

--现场掌纹触发器
CREATE OR REPLACE TRIGGER HALL_NORMALLP_LATPALM
  AFTER
  INSERT
  OR UPDATE
  OR DELETE ON AFIS.NORMALLP_LATPALM FOR EACH ROW
DECLARE palmid VARCHAR2(32);
BEGIN
  IF :OLD.palmid IS NULL THEN
    palmid := :NEW.palmid;
  ELSE
    palmid := :OLD.palmid;
  END IF;
  DELETE FROM HALL_NORMALLP_LATPALM WHERE HALL_NORMALLP_LATPALM.sid = palmid;
  INSERT INTO HALL_NORMALLP_LATPALM VALUES(palmid, HALL_NORMALLP_LATPALM_SEQ.nextval);
END;

--查询队列序列
create sequence HALL_NORMALQUERY_QUERYQUE_SEQ
minvalue 1
maxvalue 9999999999999999999
start with 1
increment by 1
cache 20;

--查询队列触发器
CREATE OR REPLACE TRIGGER HALL_NORMALQUERY_QUERYQUE
  BEFORE
  INSERT ON AFIS.NORMALQUERY_QUERYQUE
  REFERENCING NEW AS NEW_VALUE
  FOR EACH ROW
BEGIN
  SELECT HALL_NORMALQUERY_QUERYQUE_SEQ.NEXTVAL INTO :NEW_VALUE.seq FROM DUAL;
END;
