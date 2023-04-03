CREATE DATABASE  IF NOT EXISTS `siac` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_general_ci */;
USE `siac`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: siac
-- ------------------------------------------------------
-- Server version	5.6.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alerta`
--

DROP TABLE IF EXISTS `alerta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alerta` (
  `ale_id` int(11) NOT NULL AUTO_INCREMENT,
  `ale_numero` int(11) DEFAULT NULL,
  `ale_msg` varchar(500) CHARACTER SET utf8 DEFAULT NULL,
  `ale_leitura_fk` int(11) DEFAULT NULL,
  `ale_atividade_fk` int(11) DEFAULT NULL,
  `ale_animal_fk` int(11) DEFAULT NULL,
  `ale_cio_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`ale_id`),
  KEY `fk_alerta_leitura_idx` (`ale_leitura_fk`),
  KEY `fk_alerta_atividade_idx` (`ale_atividade_fk`),
  KEY `fk_alerta_animal_idx` (`ale_animal_fk`),
  KEY `fk_alerta_cio_idx` (`ale_cio_fk`),
  CONSTRAINT `fk_alerta_animal1` FOREIGN KEY (`ale_animal_fk`) REFERENCES `animal` (`ani_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_alerta_atividade1` FOREIGN KEY (`ale_atividade_fk`) REFERENCES `atividade_animal` (`atv_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_alerta_cio1` FOREIGN KEY (`ale_cio_fk`) REFERENCES `cio` (`cio_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_alerta_leitura1` FOREIGN KEY (`ale_leitura_fk`) REFERENCES `leitura` (`lei_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alerta`
--

LOCK TABLES `alerta` WRITE;
/*!40000 ALTER TABLE `alerta` DISABLE KEYS */;
INSERT INTO `alerta` VALUES (35,334,'NOVO CICLO PERCEBIDO: Ciclo Estral cadastrado com o status PREVISTO para o animal 0000003 - VLA303. Proceda com a Avaliacao.\n Hora de Cadastro: 12/02/2015 02:00:00',1348,1340,3,34),(36,735,'NOVO CICLO PERCEBIDO: Ciclo Estral cadastrado com o status PREVISTO para o animal 0000007 - VA007. Proceda com a Avaliacao.\n Hora de Cadastro: 14/02/2015 02:00:00',1355,1347,7,35),(37,136,'NOVO CICLO PERCEBIDO: Ciclo Estral cadastrado com o status PREVISTO para o animal 0000001 - VL100. Proceda com a Avaliacao.\n Hora de Cadastro: 20/02/2015 02:00:00',1386,1378,1,36),(38,136,'ALTERACAO NO CICLO: Ciclo Estral com Avaliacao Pendente - O ultimo registro de Cio estabelecido sob o codigo 9476173 referente ao animal 0000001 - VL100 sofreu alteracoes.\n Hora de Atualizacao: 21/02/2015 14:00:00',1395,1387,1,36),(39,737,'NOVO CICLO PERCEBIDO: Ciclo Estral cadastrado com o status SUSPEITO para o animal 0000007 - VA007. Proceda com a Avaliacao.\n Hora de Cadastro: 22/02/2015 14:00:00',1403,1395,7,37);
/*!40000 ALTER TABLE `alerta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alertas_emitidos`
--

DROP TABLE IF EXISTS `alertas_emitidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alertas_emitidos` (
  `alem_id` int(11) NOT NULL AUTO_INCREMENT,
  `alem_data` date DEFAULT NULL,
  `alem_hora` time DEFAULT NULL,
  `alem_alerta_fk` int(11) DEFAULT NULL,
  `alem_destinatario_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`alem_id`),
  KEY `fk_alem_destinatario_idx` (`alem_destinatario_fk`),
  KEY `fk_alem_alerta_idx` (`alem_alerta_fk`),
  CONSTRAINT `fk_alerta_has_destinatario_alerta1` FOREIGN KEY (`alem_alerta_fk`) REFERENCES `alerta` (`ale_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_alerta_has_destinatario_destinatario1` FOREIGN KEY (`alem_destinatario_fk`) REFERENCES `destinatario` (`des_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=225 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alertas_emitidos`
--

LOCK TABLES `alertas_emitidos` WRITE;
/*!40000 ALTER TABLE `alertas_emitidos` DISABLE KEYS */;
INSERT INTO `alertas_emitidos` VALUES (214,'2015-02-12','02:00:00',35,1),(215,'2015-02-14','02:00:00',36,1),(216,'2015-02-20','02:00:00',37,1),(217,'2015-02-21','14:00:00',38,1),(218,'2015-02-22','02:00:00',38,1),(219,'2015-02-22','14:00:00',38,1),(220,'2015-02-22','14:00:00',39,1),(221,'2015-02-23','02:00:00',39,1),(222,'2015-02-23','14:00:00',39,1),(223,'2015-02-24','02:00:00',39,1),(224,'2015-02-24','14:00:00',39,1);
/*!40000 ALTER TABLE `alertas_emitidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `animal`
--

DROP TABLE IF EXISTS `animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `animal` (
  `ani_id` int(11) NOT NULL AUTO_INCREMENT,
  `ani_codigo` varchar(10) DEFAULT NULL,
  `ani_apelido` varchar(255) DEFAULT NULL,
  `ani_rgn` int(11) DEFAULT NULL,
  `ani_sexo` varchar(10) DEFAULT NULL,
  `ani_grau_sangue` varchar(50) DEFAULT NULL COMMENT 'PO - Puro Origem',
  `ani_tipo` varchar(255) DEFAULT NULL COMMENT 'Boi para engorda',
  `ani_raca` varchar(255) DEFAULT NULL,
  `ani_pelagem` varchar(255) DEFAULT NULL,
  `ani_dt_nascimento` date DEFAULT NULL,
  `ani_dt_cadastro` date DEFAULT NULL,
  `ani_hr_cadastro` time DEFAULT NULL,
  `ani_tipo_geracao` varchar(255) DEFAULT NULL COMMENT 'Normal (boi ou touro)',
  `ani_cod_mae` int(11) DEFAULT NULL,
  `ani_cod_pai` int(11) DEFAULT NULL,
  `ani_estado_atual` varchar(255) DEFAULT NULL COMMENT 'inseminada',
  `ani_peso_nascimento` decimal(6,2) DEFAULT NULL,
  `ani_peso_cadastro` decimal(6,2) DEFAULT NULL,
  `ani_peso_atual` decimal(6,2) DEFAULT NULL,
  `ani_preco_compra` decimal(11,2) DEFAULT NULL,
  `ani_imagem` varchar(255) DEFAULT NULL,
  `ani_observacao` varchar(500) DEFAULT NULL,
  `ani_monitorando` int(1) DEFAULT NULL COMMENT '0=Nao, 1=Sim',
  `ani_propriedade_fk` int(11) DEFAULT NULL,
  `ani_lote_fk` int(11) DEFAULT NULL,
  `ani_pasto_fk` int(11) DEFAULT NULL,
  `ani_sensor_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`ani_id`),
  KEY `fk_animal_lote_idx` (`ani_lote_fk`),
  KEY `fk_animal_pasto_idx` (`ani_pasto_fk`),
  KEY `fk_animal_propriedade_idx` (`ani_propriedade_fk`),
  KEY `fk_animal_sensor_idx` (`ani_sensor_fk`),
  CONSTRAINT `fk_animal_lote1` FOREIGN KEY (`ani_lote_fk`) REFERENCES `lote` (`lot_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_animal_pasto1` FOREIGN KEY (`ani_pasto_fk`) REFERENCES `pasto` (`pas_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_animal_propriedade1` FOREIGN KEY (`ani_propriedade_fk`) REFERENCES `propriedade` (`prop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_animal_sensor1` FOREIGN KEY (`ani_sensor_fk`) REFERENCES `sensor` (`sen_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animal`
--

LOCK TABLES `animal` WRITE;
/*!40000 ALTER TABLE `animal` DISABLE KEYS */;
INSERT INTO `animal` VALUES (1,'0000001','VL100',1111,'','PURO ORIGEM INTERNACIONAL (POI)','MATRIZ LEITEIRA','NELORE','','1988-02-12','2013-12-22','15:34:09','SEMEN',0,0,'VACA SECA',0.00,0.00,0.00,0.00,NULL,'',1,1,1,1,1),(2,'0000002','BBBB',0,'F','PURO ORIGEM INTERNACIONAL (POI)','BEZERRO','NELORE','BRANCA','1988-02-12','2013-12-22','15:34:09','NORMAL',0,0,'VACA SECA',0.00,0.00,0.00,0.00,NULL,'',0,1,1,1,NULL),(3,'0000003','VLA303',3333,'','PURO ORIGEM INTERNACIONAL (POI)','MATRIZ LEITEIRA','NELORE','','1988-02-12','2013-12-22','15:34:09','NORMAL',0,0,'VACA SECA',0.00,0.00,0.00,0.00,NULL,'',1,1,1,1,3),(7,'0000007','VA007',7777,'','PURO ORIGEM INTERNACIONAL (POI)','MATRIZ LEITEIRA','NELORE','','1988-02-12','2013-12-22','15:34:09','NORMAL',0,0,'VACA SECA',0.00,0.00,0.00,0.00,NULL,'',1,1,1,1,7);
/*!40000 ALTER TABLE `animal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atividade_animal`
--

DROP TABLE IF EXISTS `atividade_animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atividade_animal` (
  `atv_id` int(11) NOT NULL AUTO_INCREMENT,
  `atv_variacao_passadas` int(11) DEFAULT NULL,
  `atv_perc_variacao_passadas` decimal(9,2) DEFAULT NULL COMMENT 'Percentagem de passadas em comparação a media de passadas por hora do animal',
  `atv_classificacao` varchar(45) DEFAULT NULL COMMENT 'Baixa (até 75% de variação), Média (76% a 125% de variação), Alta (acima de 126% de variação), na atividade, comparando as passadas da leitura com o padrao de passadas do animal',
  `atv_leitura_fk` int(11) DEFAULT NULL,
  `atv_animal_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`atv_id`),
  KEY `fk_atividade_leitura_idx` (`atv_leitura_fk`),
  KEY `fk_atividade_animal_idx` (`atv_animal_fk`),
  CONSTRAINT `fk_atividade_animal1` FOREIGN KEY (`atv_animal_fk`) REFERENCES `animal` (`ani_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_atividade_leitura1` FOREIGN KEY (`atv_leitura_fk`) REFERENCES `leitura` (`lei_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1410 DEFAULT CHARSET=utf8 COMMENT='Tabela que classifica a atividade do animal a partir da leitura de passadas';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atividade_animal`
--

LOCK TABLES `atividade_animal` WRITE;
/*!40000 ALTER TABLE `atividade_animal` DISABLE KEYS */;
INSERT INTO `atividade_animal` VALUES (1282,-25,-41.00,'BAIXA',1290,1),(1283,-11,-27.00,'BAIXA',1291,3),(1284,-25,-31.00,'BAIXA',1292,7),(1285,21,60.00,'BAIXA',1293,1),(1286,13,44.00,'BAIXA',1294,3),(1287,32,58.00,'BAIXA',1295,7),(1288,16,35.00,'BAIXA',1296,1),(1289,4,11.00,'BAIXA',1297,3),(1290,5,7.00,'BAIXA',1298,7),(1291,13,26.00,'BAIXA',1299,1),(1292,5,13.00,'BAIXA',1300,3),(1293,5,6.00,'BAIXA',1301,7),(1294,1,1.00,'BAIXA',1302,1),(1295,-3,-8.00,'BAIXA',1303,3),(1296,7,9.00,'BAIXA',1304,7),(1297,5,9.00,'BAIXA',1305,1),(1298,3,8.00,'BAIXA',1306,3),(1299,4,5.00,'BAIXA',1307,7),(1300,1,1.00,'BAIXA',1308,1),(1301,-4,-10.00,'BAIXA',1309,3),(1302,-5,-6.00,'BAIXA',1310,7),(1303,4,7.00,'BAIXA',1311,1),(1304,2,5.00,'BAIXA',1312,3),(1305,5,6.00,'BAIXA',1313,7),(1306,-3,-5.00,'BAIXA',1314,1),(1307,0,0.00,'BAIXA',1315,3),(1308,-7,-9.00,'BAIXA',1316,7),(1309,-4,-7.00,'BAIXA',1317,1),(1310,2,5.00,'BAIXA',1318,3),(1311,5,6.00,'BAIXA',1319,7),(1312,0,0.00,'BAIXA',1320,1),(1313,3,8.00,'BAIXA',1321,3),(1314,0,0.00,'BAIXA',1322,7),(1315,4,7.00,'BAIXA',1323,1),(1316,-1,-2.00,'BAIXA',1324,3),(1317,4,5.00,'BAIXA',1325,7),(1318,-1,-1.00,'BAIXA',1326,1),(1319,0,0.00,'BAIXA',1327,3),(1320,-2,-2.00,'BAIXA',1328,7),(1321,-4,-7.00,'BAIXA',1329,1),(1322,-4,-10.00,'BAIXA',1330,3),(1323,-4,-5.00,'BAIXA',1331,7),(1324,1,1.00,'BAIXA',1332,1),(1325,1,2.00,'BAIXA',1333,3),(1326,0,0.00,'BAIXA',1334,7),(1327,3,5.00,'BAIXA',1335,1),(1328,1,2.00,'BAIXA',1336,3),(1329,2,2.00,'BAIXA',1337,7),(1330,-1,-1.00,'BAIXA',1338,1),(1331,-1,-2.00,'BAIXA',1339,3),(1332,1,1.00,'BAIXA',1340,7),(1333,5,9.00,'BAIXA',1341,1),(1334,0,0.00,'BAIXA',1342,3),(1335,-1,-1.00,'BAIXA',1343,7),(1336,3,5.00,'BAIXA',1344,1),(1337,-4,-10.00,'BAIXA',1345,3),(1338,-6,-8.00,'BAIXA',1346,7),(1339,-4,-7.00,'BAIXA',1347,1),(1340,1,2.00,'BAIXA',1348,3),(1341,0,0.00,'BAIXA',1349,7),(1342,-4,-7.00,'BAIXA',1350,1),(1343,2,5.00,'BAIXA',1351,3),(1344,3,4.00,'BAIXA',1352,7),(1345,4,7.00,'BAIXA',1353,1),(1346,0,0.00,'BAIXA',1354,3),(1347,0,0.00,'BAIXA',1355,7),(1348,4,7.00,'BAIXA',1356,1),(1349,-3,-8.00,'BAIXA',1357,3),(1350,5,6.00,'BAIXA',1358,7),(1351,2,3.00,'BAIXA',1359,1),(1352,-1,-2.00,'BAIXA',1360,3),(1353,2,2.00,'BAIXA',1361,7),(1354,2,3.00,'BAIXA',1362,1),(1355,0,0.00,'BAIXA',1363,3),(1356,-7,-9.00,'BAIXA',1364,7),(1357,0,0.00,'BAIXA',1365,1),(1358,0,0.00,'BAIXA',1366,3),(1359,3,4.00,'BAIXA',1367,7),(1360,0,0.00,'BAIXA',1368,1),(1361,-1,-2.00,'BAIXA',1369,3),(1362,-7,-9.00,'BAIXA',1370,7),(1363,-3,-5.00,'BAIXA',1371,1),(1364,1,2.00,'BAIXA',1372,3),(1365,-6,-8.00,'BAIXA',1373,7),(1366,3,5.00,'BAIXA',1374,1),(1367,-1,-2.00,'BAIXA',1375,3),(1368,0,0.00,'BAIXA',1376,7),(1369,4,7.00,'BAIXA',1377,1),(1370,-1,-2.00,'BAIXA',1378,3),(1371,1,1.00,'BAIXA',1379,7),(1372,-4,-7.00,'BAIXA',1380,1),(1373,1,2.00,'BAIXA',1381,3),(1374,0,0.00,'BAIXA',1382,7),(1375,-3,-5.00,'BAIXA',1383,1),(1376,-1,-2.00,'BAIXA',1384,3),(1377,-8,-10.00,'BAIXA',1385,7),(1378,-1,-1.00,'BAIXA',1386,1),(1379,2,5.00,'BAIXA',1387,3),(1380,3,4.00,'BAIXA',1388,7),(1381,-3,-5.00,'BAIXA',1389,1),(1382,117,325.00,'ALTA',1390,3),(1383,-7,-9.00,'BAIXA',1391,7),(1384,5,9.00,'BAIXA',1392,1),(1385,118,302.00,'ALTA',1393,3),(1386,2,2.00,'BAIXA',1394,7),(1387,54,100.00,'MEDIA',1395,1),(1388,-3,-6.00,'BAIXA',1396,3),(1389,-2,-2.00,'BAIXA',1397,7),(1390,50,90.00,'MEDIA',1398,1),(1391,-5,-11.00,'BAIXA',1399,3),(1392,4,5.00,'BAIXA',1400,7),(1393,98,171.00,'ALTA',1401,1),(1394,-2,-4.00,'BAIXA',1402,3),(1395,210,287.00,'ALTA',1403,7),(1396,-7,-11.00,'BAIXA',1404,1),(1397,-4,-9.00,'BAIXA',1405,3),(1398,126,159.00,'ALTA',1406,7),(1399,-6,-10.00,'BAIXA',1407,1),(1400,-1,-2.00,'BAIXA',1408,3),(1401,224,273.00,'ALTA',1409,7),(1402,0,0.00,'BAIXA',1410,1),(1403,2,4.00,'BAIXA',1411,3),(1404,-18,-20.00,'BAIXA',1412,7),(1405,-9,-15.00,'BAIXA',1413,1),(1406,2,4.00,'BAIXA',1414,3),(1407,-16,-18.00,'BAIXA',1415,7),(1408,-6,-10.00,'BAIXA',1416,1),(1409,2,4.00,'BAIXA',1417,3);
/*!40000 ALTER TABLE `atividade_animal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `central`
--

DROP TABLE IF EXISTS `central`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `central` (
  `cen_id` int(11) NOT NULL AUTO_INCREMENT,
  `cen_codigo` varchar(8) DEFAULT NULL,
  `cen_descricao` varchar(255) DEFAULT NULL,
  `cen_nserie` varchar(255) DEFAULT NULL,
  `cen_local_fixacao` varchar(255) DEFAULT NULL,
  `cen_tecnologia` varchar(255) DEFAULT NULL COMMENT 'Infravermelho, RadioFrequencia ou Telemetria',
  `cen_observacao` varchar(500) DEFAULT NULL,
  `cen_qtd_sensores` int(11) DEFAULT NULL,
  `cen_ativa` int(1) DEFAULT NULL,
  PRIMARY KEY (`cen_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `central`
--

LOCK TABLES `central` WRITE;
/*!40000 ALTER TABLE `central` DISABLE KEYS */;
INSERT INTO `central` VALUES (1,'00000001','Central 001','UP00001','CURRAL',NULL,NULL,10,1);
/*!40000 ALTER TABLE `central` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cio`
--

DROP TABLE IF EXISTS `cio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cio` (
  `cio_id` int(11) NOT NULL AUTO_INCREMENT,
  `cio_codigo` varchar(45) DEFAULT NULL,
  `cio_dt_registro` date DEFAULT NULL COMMENT 'Campo será alterado cada vez que o usuário ou o próprio sistema cadastrar o cio',
  `cio_hr_registro` time DEFAULT NULL,
  `cio_status` varchar(255) DEFAULT NULL COMMENT 'SUSPEITO, CONFIRMADO, DESCARTADO, ENCERRADO',
  `cio_dt_ult_alteracao` date DEFAULT NULL COMMENT 'Campo será alterado cada vez que o usuário ou o próprio sistema mudar o status do cio',
  `cio_hr_ult_alteracao` time DEFAULT NULL,
  `cio_mtd_id` varchar(255) DEFAULT NULL COMMENT 'Método de identificação do cio: Observação Humana, Sensoriamento',
  `cio_mtd_registro` varchar(255) DEFAULT NULL COMMENT 'manual, automatico',
  `cio_dt_inicio` date DEFAULT NULL,
  `cio_hr_inicio` time DEFAULT NULL,
  `cio_dt_prev_termino` date DEFAULT NULL,
  `cio_hr_prev_termino` time DEFAULT NULL,
  `cio_dt_termino` date DEFAULT NULL,
  `cio_hr_termino` time DEFAULT NULL,
  `cio_duracao` int(11) DEFAULT NULL,
  `cio_observacao` varchar(500) DEFAULT NULL,
  `cio_animal_fk` int(11) DEFAULT NULL,
  `cio_leitura_fk` int(11) DEFAULT NULL,
  `cio_atividade_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`cio_id`),
  KEY `fk_cio_animal_idx` (`cio_animal_fk`),
  KEY `fk_cio_leitura_idx` (`cio_leitura_fk`),
  KEY `fk_cio_atividade_animal_idx` (`cio_atividade_fk`),
  CONSTRAINT `fk_cio_animal1` FOREIGN KEY (`cio_animal_fk`) REFERENCES `animal` (`ani_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cio_atividade_animal1` FOREIGN KEY (`cio_atividade_fk`) REFERENCES `atividade_animal` (`atv_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cio_leitura1` FOREIGN KEY (`cio_leitura_fk`) REFERENCES `leitura` (`lei_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cio`
--

LOCK TABLES `cio` WRITE;
/*!40000 ALTER TABLE `cio` DISABLE KEYS */;
INSERT INTO `cio` VALUES (34,'952935','2015-02-12','02:00:00','ENCERRADO','2015-02-25','02:00:00','INFERENCIA','AUTOMATICO','2015-02-12','02:00:00','2015-02-13','02:00:00','2015-02-25','02:00:00',36,'Chegada a DATA PREVISTA para que o animal entre em estado de cio, apesar de nÃ£o haver Desvio no padrÃ£o de movimentaÃ§Ã£o do animal.',3,1348,1340),(35,'956755','2015-02-14','02:00:00','CONFIRMADO','2015-02-25','02:00:00','INFERENCIA','AUTOMATICO','2015-02-14','02:00:00','2015-02-15','16:00:00',NULL,NULL,24,'Chegada a DATA PREVISTA para que o animal entre em estado de cio, apesar de nÃ£o haver Desvio no padrÃ£o de movimentaÃ§Ã£o do animal.',7,1355,1347),(36,'9476173','2015-02-20','02:00:00','CONFIRMADO','2015-02-25','02:00:00','INFERENCIA','AUTOMATICO','2015-02-20','02:00:00','2015-02-21','14:00:00',NULL,NULL,120,'Constatado desvio no padrÃ£o de movimentaÃ§Ã£o do animal que indica o estado de cio; O Tempo em anestro Ã© considerado NORMAL ou LONGO.',1,1386,1378),(37,'9199730','2015-02-22','14:00:00','CONFIRMADO','2015-02-25','02:00:00','INFERENCIA','AUTOMATICO','2015-02-22','14:00:00','2015-02-24','04:00:00',NULL,NULL,13,'Constatado desvio no padrÃ£o de movimentaÃ§Ã£o do animal que indica o estado de cio; O Tempo em anestro Ã© considerado NORMAL ou LONGO.',7,1403,1395);
/*!40000 ALTER TABLE `cio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config`
--

DROP TABLE IF EXISTS `config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `config` (
  `cfg_id` int(11) NOT NULL AUTO_INCREMENT,
  `cfg_dt_ult_captura` date DEFAULT NULL,
  `cfg_hr_ult_captura` time DEFAULT NULL,
  `cfg_reg_sensores` varchar(45) DEFAULT NULL COMMENT 'Opção para carregamento dos registros dos sensores ao iniciar a aplicação (Manual ou Automático)',
  `cfg_intervalo_entre_leituras` int(11) DEFAULT NULL COMMENT 'em horas (minimo 2)... intervale de carregamento de leituras',
  `cfg_data_atual` date DEFAULT NULL,
  `cfg_hora_atual` time DEFAULT NULL,
  `cfg_perc_min_atv_media` int(11) DEFAULT NULL,
  `cfg_perc_min_atv_alta` int(11) DEFAULT NULL,
  `cfg_duracao_cio` int(11) DEFAULT NULL,
  `cfg_intervalo_anestro` int(11) DEFAULT NULL,
  `cfg_alerta_quando` varchar(45) DEFAULT NULL COMMENT 'Identifica o cio',
  `cfg_qtd_alertas_ocorrencia` int(11) DEFAULT NULL COMMENT 'qtd de alertas por ocorrencia',
  `cfg_tempo_entre_alertas` time DEFAULT NULL COMMENT 'intervalo entre os alertas',
  `cfg_oco_pra_cio` int(11) DEFAULT NULL COMMENT 'Qtd de ocorrencias para',
  `cfg_msg_alerta_cio` varchar(5000) DEFAULT NULL,
  `cfg_msg_alerta_ocorrencia` varchar(5000) DEFAULT NULL,
  `cfg_envio_email` varchar(1) DEFAULT NULL COMMENT 's ou n',
  `cfg_envio_celular` varchar(1) DEFAULT NULL COMMENT 's ou n',
  `cfg_min_anestro_normal` int(2) DEFAULT NULL,
  `cfg_min_anestro_longo` int(2) DEFAULT NULL,
  `cfg_min_anestro_mlongo` int(2) DEFAULT NULL,
  PRIMARY KEY (`cfg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config`
--

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` VALUES (1,'2015-02-25','02:00:00','manual',12,'2015-02-02','01:59:35',76,126,0,0,'Suspeitar e Constatar Cio',5,'00:00:00',1,'Cio','Suspeita de Cio','n','n',15,24,30);
/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `destinatario`
--

DROP TABLE IF EXISTS `destinatario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `destinatario` (
  `des_id` int(11) NOT NULL AUTO_INCREMENT,
  `des_email` varchar(255) DEFAULT NULL,
  `des_funcionario_fk` int(11) DEFAULT NULL,
  `des_ativo` int(1) DEFAULT '0' COMMENT '0=Nao, 1=Sim',
  `des_celular` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`des_id`),
  KEY `fk_destinatario_funcionario_idx` (`des_funcionario_fk`),
  CONSTRAINT `fk_destinatario_funcionario1` FOREIGN KEY (`des_funcionario_fk`) REFERENCES `funcionario` (`func_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `destinatario`
--

LOCK TABLES `destinatario` WRITE;
/*!40000 ALTER TABLE `destinatario` DISABLE KEYS */;
INSERT INTO `destinatario` VALUES (1,'vitorvilasboas@ifto.edu.br',1,1,'6399752543'),(2,'vitormendesvilasboas@gmail.com',2,0,'6381542043'),(3,'ti.araguatins@ifto.edu.br',1,0,'6381016623');
/*!40000 ALTER TABLE `destinatario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funcionario`
--

DROP TABLE IF EXISTS `funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `funcionario` (
  `func_id` int(11) NOT NULL AUTO_INCREMENT,
  `func_matricula` varchar(255) DEFAULT NULL,
  `func_nome` varchar(255) DEFAULT NULL,
  `func_cpf` varchar(14) DEFAULT NULL,
  `func_rg` varchar(255) DEFAULT NULL,
  `func_orgao_emissor` varchar(255) DEFAULT NULL,
  `func_pis` varchar(255) DEFAULT NULL,
  `func_pai` varchar(255) DEFAULT NULL,
  `func_mae` varchar(255) DEFAULT NULL,
  `func_endereco` varchar(255) DEFAULT NULL,
  `func_cidade` varchar(255) DEFAULT NULL,
  `func_uf` varchar(2) DEFAULT NULL,
  `func_dt_nascimento` date DEFAULT NULL,
  `func_dt_admissao` date DEFAULT NULL,
  `func_cargo` varchar(255) DEFAULT NULL COMMENT 'Capataz, Boiadeiro, Inseminador, Serviços Gerais, Peão ou Fazendeiro',
  `func_fone` varchar(255) DEFAULT NULL,
  `func_email` varchar(255) DEFAULT NULL,
  `func_login` varchar(255) DEFAULT NULL,
  `func_senha` varchar(255) DEFAULT NULL,
  `func_propriedade_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`func_id`),
  KEY `fk_funcionario_propriedade_idx` (`func_propriedade_fk`),
  CONSTRAINT `fk_funcionario_propriedade1` FOREIGN KEY (`func_propriedade_fk`) REFERENCES `propriedade` (`prop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionario`
--

LOCK TABLES `funcionario` WRITE;
/*!40000 ALTER TABLE `funcionario` DISABLE KEYS */;
INSERT INTO `funcionario` VALUES (1,'1','TESTE FUNC 1','999.999.999-99','111.11111','SSP-TO','94949494','PAI TESTE','MAE TESTE','RUA B N 757','ARAGUATINS','TO','1988-12-02','2013-09-02','CAPATAZ','9999-9898','testefunc1@gmail.com','teste1','e959088c6049f1104c84c9bde5560a13',1),(2,'2','TESTE FUNC 2','888.888.888-88','222.22222','SSP-MA','83838383','FATHER','MOTHER','10004 SUL AL 4','AUGUSTINOPOLIS','TO','1977-10-05','2012-10-01','PEÃO','8888-8787','functeste2@hotmail.com','teste2','38851536d87701d2191990e24a7f8d4e',2);
/*!40000 ALTER TABLE `funcionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `info_animal`
--

DROP TABLE IF EXISTS `info_animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info_animal` (
  `info_id` int(11) NOT NULL AUTO_INCREMENT,
  `info_dt_ini_ult_cio` date DEFAULT NULL,
  `info_hr_ini_ult_cio` time DEFAULT NULL,
  `info_dt_fim_ult_cio` date DEFAULT NULL,
  `info_hr_fim_ult_cio` time DEFAULT NULL,
  `info_dt_prev_ini_prox_cio` date DEFAULT NULL,
  `info_hr_prev_ini_prox_cio` time DEFAULT NULL,
  `info_duracao_media_cio` int(11) DEFAULT NULL,
  `info_intervalo_padrao_anestro` int(11) DEFAULT NULL COMMENT 'Qtd media de dias entre cios ',
  `info_dias_em_anestro` int(11) DEFAULT NULL,
  `info_tempo_atual_anestro` varchar(255) DEFAULT NULL COMMENT 'curto (0 - 15 dias), normal (15 a 25 dias), longo (25 a 35 dias) , muito longo (Acima de 35 dias)',
  `info_media_passos_hora` int(11) DEFAULT NULL,
  `info_status_atividade` varchar(255) DEFAULT NULL COMMENT 'Baixa (até 75% de variação), Média (76% a 125% de variação), Alta (acima de 126% de variação)',
  `info_aprox_cio` int(1) DEFAULT NULL COMMENT 'Varia de 0 a 9, qto maior, mais próximo do próximo cio (9 para cio e 0 para pós cio), aumenta de acordo com a prev_ini_prox_cio',
  `info_status_cio` int(1) DEFAULT NULL,
  `info_sob_alerta` int(11) DEFAULT NULL COMMENT 'armazena a qtd de alertas do cio atual',
  `info_idade` int(11) DEFAULT NULL,
  `info_oco_produtiva` varchar(255) DEFAULT NULL COMMENT 'MUITO BAIXA, BAIXA, NORMAL, ALTA, MUITO ALTA',
  `info_oco_reprodutiva` varchar(255) DEFAULT NULL COMMENT 'MUITO BAIXA, BAIXA, NORMAL, ALTA, MUITO ALTA',
  `info_animal_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`info_id`),
  KEY `fk_info_animal_animal_idx` (`info_animal_fk`),
  CONSTRAINT `fk_info_animal_animal1` FOREIGN KEY (`info_animal_fk`) REFERENCES `animal` (`ani_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `info_animal`
--

LOCK TABLES `info_animal` WRITE;
/*!40000 ALTER TABLE `info_animal` DISABLE KEYS */;
INSERT INTO `info_animal` VALUES (1,'2015-02-20','02:00:00','2015-01-31','14:00:00','2015-03-12','00:00:00',36,19,0,'PRINCIPIO',59,'BAIXA',10,1,0,0,'NORMAL','NORMAL',1),(2,'2014-12-01','08:00:00','2014-12-02','10:00:00','2014-12-24','10:00:00',0,22,54,'MUITO LONGO',30,'',24,0,0,0,'NORMAL','NORMAL',2),(3,'2015-02-12','02:00:00','2015-01-24','02:00:00','2015-02-12','02:00:00',24,19,32,'MUITO LONGO',42,'BAIXA',10,1,0,0,'NORMAL','NORMAL',3),(7,'2015-02-22','14:00:00','2015-01-24','22:00:00','2015-03-20','00:00:00',38,24,0,'PRINCIPIO',87,'BAIXA',10,1,0,0,'NORMAL','NORMAL',7);
/*!40000 ALTER TABLE `info_animal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leitura`
--

DROP TABLE IF EXISTS `leitura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `leitura` (
  `lei_id` int(11) NOT NULL AUTO_INCREMENT,
  `lei_numero` int(11) DEFAULT NULL,
  `lei_qtd_passadas` int(11) DEFAULT NULL,
  `lei_data` date DEFAULT NULL,
  `lei_hora` time DEFAULT NULL,
  `lei_observacao` varchar(255) DEFAULT NULL,
  `lei_sensor_fk` int(11) DEFAULT NULL,
  `lei_central_fk` int(11) DEFAULT NULL,
  `lei_intervalo_config` int(11) DEFAULT NULL,
  PRIMARY KEY (`lei_id`),
  KEY `fk_leitura_sensor_idx` (`lei_sensor_fk`),
  KEY `fk_leitura_central_idx` (`lei_central_fk`),
  CONSTRAINT `fk_leitura_central1` FOREIGN KEY (`lei_central_fk`) REFERENCES `central` (`cen_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_leitura_sensor1` FOREIGN KEY (`lei_sensor_fk`) REFERENCES `sensor` (`sen_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1418 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leitura`
--

LOCK TABLES `leitura` WRITE;
/*!40000 ALTER TABLE `leitura` DISABLE KEYS */;
INSERT INTO `leitura` VALUES (1290,0,420,'2015-02-01','02:00:00',NULL,1,1,12),(1291,0,352,'2015-02-01','02:00:00',NULL,3,1,12),(1292,0,671,'2015-02-01','02:00:00',NULL,7,1,12),(1293,0,678,'2015-02-01','14:00:00',NULL,1,1,12),(1294,0,511,'2015-02-01','14:00:00',NULL,3,1,12),(1295,0,1045,'2015-02-01','14:00:00',NULL,7,1,12),(1296,0,738,'2015-02-02','02:00:00',NULL,1,1,12),(1297,0,474,'2015-02-02','02:00:00',NULL,3,1,12),(1298,0,918,'2015-02-02','02:00:00',NULL,7,1,12),(1299,0,764,'2015-02-02','14:00:00',NULL,1,1,12),(1300,0,500,'2015-02-02','14:00:00',NULL,3,1,12),(1301,0,931,'2015-02-02','14:00:00',NULL,7,1,12),(1302,0,659,'2015-02-03','14:00:00',NULL,1,1,12),(1303,0,419,'2015-02-03','14:00:00',NULL,3,1,12),(1304,0,961,'2015-02-03','14:00:00',NULL,7,1,12),(1305,0,696,'2015-02-04','14:00:00',NULL,1,1,12),(1306,0,481,'2015-02-04','14:00:00',NULL,3,1,12),(1307,0,959,'2015-02-04','14:00:00',NULL,7,1,12),(1308,0,671,'2015-02-05','14:00:00',NULL,1,1,12),(1309,0,405,'2015-02-05','14:00:00',NULL,3,1,12),(1310,0,843,'2015-02-05','14:00:00',NULL,7,1,12),(1311,0,705,'2015-02-06','02:00:00',NULL,1,1,12),(1312,0,458,'2015-02-06','02:00:00',NULL,3,1,12),(1313,0,952,'2015-02-06','02:00:00',NULL,7,1,12),(1314,0,630,'2015-02-06','14:00:00',NULL,1,1,12),(1315,0,453,'2015-02-06','14:00:00',NULL,3,1,12),(1316,0,816,'2015-02-06','14:00:00',NULL,7,1,12),(1317,0,609,'2015-02-07','02:00:00',NULL,1,1,12),(1318,0,475,'2015-02-07','02:00:00',NULL,3,1,12),(1319,0,958,'2015-02-07','02:00:00',NULL,7,1,12),(1320,0,655,'2015-02-07','14:00:00',NULL,1,1,12),(1321,0,480,'2015-02-07','14:00:00',NULL,3,1,12),(1322,0,901,'2015-02-07','14:00:00',NULL,7,1,12),(1323,0,704,'2015-02-08','02:00:00',NULL,1,1,12),(1324,0,437,'2015-02-08','02:00:00',NULL,3,1,12),(1325,0,957,'2015-02-08','02:00:00',NULL,7,1,12),(1326,0,646,'2015-02-08','14:00:00',NULL,1,1,12),(1327,0,451,'2015-02-08','14:00:00',NULL,3,1,12),(1328,0,883,'2015-02-08','14:00:00',NULL,7,1,12),(1329,0,602,'2015-02-09','02:00:00',NULL,1,1,12),(1330,0,404,'2015-02-09','02:00:00',NULL,3,1,12),(1331,0,854,'2015-02-09','02:00:00',NULL,7,1,12),(1332,0,667,'2015-02-09','14:00:00',NULL,1,1,12),(1333,0,457,'2015-02-09','14:00:00',NULL,3,1,12),(1334,0,889,'2015-02-09','14:00:00',NULL,7,1,12),(1335,0,690,'2015-02-10','02:00:00',NULL,1,1,12),(1336,0,459,'2015-02-10','02:00:00',NULL,3,1,12),(1337,0,917,'2015-02-10','02:00:00',NULL,7,1,12),(1338,0,643,'2015-02-10','14:00:00',NULL,1,1,12),(1339,0,435,'2015-02-10','14:00:00',NULL,3,1,12),(1340,0,909,'2015-02-10','14:00:00',NULL,7,1,12),(1341,0,708,'2015-02-11','02:00:00',NULL,1,1,12),(1342,0,444,'2015-02-11','02:00:00',NULL,3,1,12),(1343,0,886,'2015-02-11','02:00:00',NULL,7,1,12),(1344,0,684,'2015-02-11','14:00:00',NULL,1,1,12),(1345,0,406,'2015-02-11','14:00:00',NULL,3,1,12),(1346,0,823,'2015-02-11','14:00:00',NULL,7,1,12),(1347,0,601,'2015-02-12','02:00:00',NULL,1,1,12),(1348,0,453,'2015-02-12','02:00:00',NULL,3,1,12),(1349,0,899,'2015-02-12','02:00:00',NULL,7,1,12),(1350,0,601,'2015-02-13','02:00:00',NULL,1,1,12),(1351,0,465,'2015-02-13','02:00:00',NULL,3,1,12),(1352,0,929,'2015-02-13','02:00:00',NULL,7,1,12),(1353,0,705,'2015-02-14','02:00:00',NULL,1,1,12),(1354,0,432,'2015-02-14','02:00:00',NULL,3,1,12),(1355,0,897,'2015-02-14','02:00:00',NULL,7,1,12),(1356,0,700,'2015-02-15','02:00:00',NULL,1,1,12),(1357,0,402,'2015-02-15','02:00:00',NULL,3,1,12),(1358,0,953,'2015-02-15','02:00:00',NULL,7,1,12),(1359,0,679,'2015-02-15','14:00:00',NULL,1,1,12),(1360,0,427,'2015-02-15','14:00:00',NULL,3,1,12),(1361,0,914,'2015-02-15','14:00:00',NULL,7,1,12),(1362,0,674,'2015-02-16','02:00:00',NULL,1,1,12),(1363,0,443,'2015-02-16','02:00:00',NULL,3,1,12),(1364,0,812,'2015-02-16','02:00:00',NULL,7,1,12),(1365,0,654,'2015-02-16','14:00:00',NULL,1,1,12),(1366,0,432,'2015-02-16','14:00:00',NULL,3,1,12),(1367,0,933,'2015-02-16','14:00:00',NULL,7,1,12),(1368,0,655,'2015-02-17','02:00:00',NULL,1,1,12),(1369,0,428,'2015-02-17','02:00:00',NULL,3,1,12),(1370,0,805,'2015-02-17','02:00:00',NULL,7,1,12),(1371,0,620,'2015-02-17','14:00:00',NULL,1,1,12),(1372,0,451,'2015-02-17','14:00:00',NULL,3,1,12),(1373,0,821,'2015-02-17','14:00:00',NULL,7,1,12),(1374,0,692,'2015-02-18','02:00:00',NULL,1,1,12),(1375,0,426,'2015-02-18','02:00:00',NULL,3,1,12),(1376,0,889,'2015-02-18','02:00:00',NULL,7,1,12),(1377,0,696,'2015-02-18','14:00:00',NULL,1,1,12),(1378,0,425,'2015-02-18','14:00:00',NULL,3,1,12),(1379,0,900,'2015-02-18','14:00:00',NULL,7,1,12),(1380,0,605,'2015-02-19','02:00:00',NULL,1,1,12),(1381,0,453,'2015-02-19','02:00:00',NULL,3,1,12),(1382,0,893,'2015-02-19','02:00:00',NULL,7,1,12),(1383,0,615,'2015-02-19','14:00:00',NULL,1,1,12),(1384,0,423,'2015-02-19','14:00:00',NULL,3,1,12),(1385,0,803,'2015-02-19','14:00:00',NULL,7,1,12),(1386,0,647,'2015-02-20','02:00:00',NULL,1,1,12),(1387,0,457,'2015-02-20','02:00:00',NULL,3,1,12),(1388,0,921,'2015-02-20','02:00:00',NULL,7,1,12),(1389,0,622,'2015-02-20','14:00:00',NULL,1,1,12),(1390,0,1842,'2015-02-20','14:00:00',NULL,3,1,12),(1391,0,792,'2015-02-20','14:00:00',NULL,7,1,12),(1392,0,710,'2015-02-21','02:00:00',NULL,1,1,12),(1393,0,1888,'2015-02-21','02:00:00',NULL,3,1,12),(1394,0,907,'2015-02-21','02:00:00',NULL,7,1,12),(1395,0,1299,'2015-02-21','14:00:00',NULL,1,1,12),(1396,1,490,'2015-02-21','14:00:00',NULL,3,1,12),(1397,0,861,'2015-02-21','14:00:00',NULL,7,1,12),(1398,0,1262,'2015-02-22','02:00:00',NULL,1,1,12),(1399,0,467,'2015-02-22','02:00:00',NULL,3,1,12),(1400,0,924,'2015-02-22','02:00:00',NULL,7,1,12),(1401,0,1867,'2015-02-22','14:00:00',NULL,1,1,12),(1402,0,498,'2015-02-22','14:00:00',NULL,3,1,12),(1403,0,3403,'2015-02-22','14:00:00',NULL,7,1,12),(1404,1,626,'2015-02-23','02:00:00',NULL,1,1,12),(1405,0,466,'2015-02-23','02:00:00',NULL,3,1,12),(1406,0,2471,'2015-02-23','02:00:00',NULL,7,1,12),(1407,0,641,'2015-02-23','14:00:00',NULL,1,1,12),(1408,0,493,'2015-02-23','14:00:00',NULL,3,1,12),(1409,0,3682,'2015-02-23','14:00:00',NULL,7,1,12),(1410,0,712,'2015-02-24','02:00:00',NULL,1,1,12),(1411,0,539,'2015-02-24','02:00:00',NULL,3,1,12),(1412,1,843,'2015-02-24','02:00:00',NULL,7,1,12),(1413,0,608,'2015-02-24','14:00:00',NULL,1,1,12),(1414,0,528,'2015-02-24','14:00:00',NULL,3,1,12),(1415,0,859,'2015-02-24','14:00:00',NULL,7,1,12),(1416,0,636,'2015-02-25','02:00:00',NULL,1,1,12),(1417,0,530,'2015-02-25','02:00:00',NULL,3,1,12);
/*!40000 ALTER TABLE `leitura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log_cio`
--

DROP TABLE IF EXISTS `log_cio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log_cio` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `log_data` date DEFAULT NULL,
  `log_hora` time DEFAULT NULL,
  `log_operacao` varchar(255) DEFAULT NULL COMMENT 'CADASTRO MANUAL, CADASTRO AUTOMATICO, ALTERACAO DE DADOS GERAIS, ALTERACAO DE STATUS, EXCLUSAO, CONFIRMACAO, DESCARTE, ENCERRAMENTO',
  `log_status_cio` varchar(255) DEFAULT NULL COMMENT 'SUSPEITO, CONFIRMADO, DESCARTADO, ENCERRADO',
  `log_descricao` varchar(500) DEFAULT NULL,
  `log_funcionario_fk` int(11) DEFAULT NULL,
  `log_cio_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`log_id`),
  KEY `fk_log_cio_funcionario_idx` (`log_funcionario_fk`),
  KEY `fk_log_cio_cio_idx` (`log_cio_fk`),
  CONSTRAINT `fk_log_cio_cio1` FOREIGN KEY (`log_cio_fk`) REFERENCES `cio` (`cio_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_log_cio_funcionario1` FOREIGN KEY (`log_funcionario_fk`) REFERENCES `funcionario` (`func_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_cio`
--

LOCK TABLES `log_cio` WRITE;
/*!40000 ALTER TABLE `log_cio` DISABLE KEYS */;
INSERT INTO `log_cio` VALUES (79,'2015-02-12','02:00:00','CADASTRO','PREVISTO','Cadastro de um novo cio por inferência automática',NULL,34),(80,'2015-02-14','02:00:00','CADASTRO','PREVISTO','Cadastro de um novo cio por inferência automática',NULL,35),(81,'2015-02-16','14:00:00','CONFIRMADO','CONFIRMADO','Avaliação de cio: CONFIRMADO',NULL,34),(82,'2015-02-17','02:00:00','ENCERRAMENTO','ENCERRADO','Encerramento automático de cio ',NULL,34),(83,'2015-02-17','14:00:00','ENCERRAMENTO','ENCERRADO','Encerramento automático de cio ',NULL,34),(84,'2015-02-18','02:00:00','ENCERRAMENTO','ENCERRADO','Encerramento automático de cio ',NULL,34),(85,'2015-02-18','14:00:00','ENCERRAMENTO','ENCERRADO','Encerramento automático de cio ',NULL,34),(86,'2015-02-19','02:00:00','ENCERRAMENTO','ENCERRADO','Encerramento automático de cio ',NULL,34),(87,'2015-02-19','14:00:00','ENCERRAMENTO','ENCERRADO','Encerramento automático de cio ',NULL,34),(88,'2015-02-20','02:00:00','CADASTRO','PREVISTO','Cadastro de um novo cio por inferência automática',NULL,36),(89,'2015-02-20','02:00:00','ENCERRAMENTO','ENCERRADO','Encerramento automático de cio ',NULL,34),(90,'2015-02-21','14:00:00','ATUALIZACAO','SUSPEITO','Atualização do status de um cio pré-cadastrado',NULL,36),(91,'2015-02-21','14:00:00','ENCERRAMENTO','ENCERRADO','Encerramento automático de cio ',NULL,34),(92,'2015-02-22','02:00:00','ENCERRAMENTO','ENCERRADO','Encerramento automático de cio ',NULL,34),(93,'2015-02-22','14:00:00','ENCERRAMENTO','ENCERRADO','Encerramento automático de cio ',NULL,34),(94,'2015-02-22','14:00:00','CADASTRO','SUSPEITO','Cadastro de um novo cio por inferência automática',NULL,37),(95,'2015-02-23','02:00:00','ENCERRAMENTO','ENCERRADO','Encerramento automático de cio ',NULL,34),(96,'2015-02-23','14:00:00','ENCERRAMENTO','ENCERRADO','Encerramento automático de cio ',NULL,34),(97,'2015-02-24','02:00:00','ENCERRAMENTO','ENCERRADO','Encerramento automático de cio ',NULL,34),(98,'2015-02-24','14:00:00','ENCERRAMENTO','ENCERRADO','Encerramento automático de cio ',NULL,34),(99,'2015-02-25','02:00:00','ENCERRAMENTO','ENCERRADO','Encerramento automático de cio ',NULL,34),(100,'2015-02-25','02:00:00','CONFIRMADO','CONFIRMADO','Avaliação de cio: CONFIRMADO',NULL,35),(101,'2015-02-25','02:00:00','CONFIRMADO','CONFIRMADO','Alteração de cio: CONFIRMADO',NULL,36),(102,'2015-02-25','02:00:00','CONFIRMADO','CONFIRMADO','Avaliação de cio: CONFIRMADO',NULL,37);
/*!40000 ALTER TABLE `log_cio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lote`
--

DROP TABLE IF EXISTS `lote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lote` (
  `lot_id` int(11) NOT NULL AUTO_INCREMENT,
  `lot_codigo` varchar(4) DEFAULT NULL,
  `lot_descricao` varchar(255) DEFAULT NULL,
  `lot_tipo` varchar(255) DEFAULT NULL COMMENT 'Rebanho de Corte ou Rebanho Leiteiro',
  `lot_observacao` varchar(500) DEFAULT NULL,
  `lot_dt_cadastro` date DEFAULT NULL,
  `lot_propriedade_fk` int(11) DEFAULT NULL,
  `lot_pasto_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`lot_id`),
  KEY `fk_lote_propriedade_idx` (`lot_propriedade_fk`),
  KEY `fk_lote_pasto_idx` (`lot_pasto_fk`),
  CONSTRAINT `fk_lote_pasto1` FOREIGN KEY (`lot_pasto_fk`) REFERENCES `pasto` (`pas_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_lote_propriedade1` FOREIGN KEY (`lot_propriedade_fk`) REFERENCES `propriedade` (`prop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lote`
--

LOCK TABLES `lote` WRITE;
/*!40000 ALTER TABLE `lote` DISABLE KEYS */;
INSERT INTO `lote` VALUES (1,'0001','Lote A','Rebanho Leiteiro',NULL,'2014-10-18',1,1);
/*!40000 ALTER TABLE `lote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocorrencia_animal`
--

DROP TABLE IF EXISTS `ocorrencia_animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ocorrencia_animal` (
  `oco_id` int(11) NOT NULL AUTO_INCREMENT,
  `oco_data` datetime DEFAULT NULL,
  `oco_tipo` int(11) DEFAULT NULL COMMENT 'Tentativa de Inseminação, Inicio de Cio, Fim de Cio, Inicio de Gestação, Fim de Gestação, Parto, Ordenha, Emissao de Alerta, Vacina, Inicio de Lactação, Fim de Lactação',
  `oco_descricao` varchar(255) DEFAULT NULL,
  `oco_observacao` varchar(500) DEFAULT NULL,
  `oco_responsavel` int(11) DEFAULT NULL COMMENT 'Código do Funcionário Responsável pela ocorrência',
  `oco_codigo_referencia` varchar(45) DEFAULT NULL COMMENT 'id da referencia da ocorrencia',
  `oco_animal_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`oco_id`),
  KEY `fk_ocorrencias_animal_idx` (`oco_animal_fk`),
  CONSTRAINT `fk_ocorrencias_animal1` FOREIGN KEY (`oco_animal_fk`) REFERENCES `animal` (`ani_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocorrencia_animal`
--

LOCK TABLES `ocorrencia_animal` WRITE;
/*!40000 ALTER TABLE `ocorrencia_animal` DISABLE KEYS */;
/*!40000 ALTER TABLE `ocorrencia_animal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pasto`
--

DROP TABLE IF EXISTS `pasto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pasto` (
  `pas_id` int(11) NOT NULL AUTO_INCREMENT,
  `pas_codigo` varchar(4) DEFAULT NULL,
  `pas_nome` varchar(255) DEFAULT NULL,
  `pas_area` decimal(10,2) DEFAULT NULL,
  `pas_observacao` varchar(1000) DEFAULT NULL,
  `pas_imagem` varchar(255) DEFAULT NULL,
  `pas_propriedade_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`pas_id`),
  KEY `fk_pasto_propriedade_idx` (`pas_propriedade_fk`),
  CONSTRAINT `fk_pasto_propriedade` FOREIGN KEY (`pas_propriedade_fk`) REFERENCES `propriedade` (`prop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pasto`
--

LOCK TABLES `pasto` WRITE;
/*!40000 ALTER TABLE `pasto` DISABLE KEYS */;
INSERT INTO `pasto` VALUES (1,'0001','PASTO 001',1500.00,'','imagens/pastos/null',1),(2,'0002','PASTO 3',1000.00,'COM CAPIM','imagens/pastos/',1),(7,'0003','teste',123.00,'dasasds','/siac/imagens/pastos/IMG_20140207_175119391_HDR.jpg',1),(8,'0004','Ssa',12.00,'sddcsCDSCAC','imagens/pastos/null',1);
/*!40000 ALTER TABLE `pasto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `previsao_cio`
--

DROP TABLE IF EXISTS `previsao_cio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `previsao_cio` (
  `prevc_id` int(11) NOT NULL AUTO_INCREMENT,
  `prevc_data` date DEFAULT NULL,
  `prevc_hora` time DEFAULT NULL,
  `prevc_animal_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`prevc_id`),
  KEY `fk_previsao_cio_animal_idx` (`prevc_animal_fk`),
  CONSTRAINT `fk_previsao_cio_animal1` FOREIGN KEY (`prevc_animal_fk`) REFERENCES `animal` (`ani_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `previsao_cio`
--

LOCK TABLES `previsao_cio` WRITE;
/*!40000 ALTER TABLE `previsao_cio` DISABLE KEYS */;
/*!40000 ALTER TABLE `previsao_cio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `propriedade`
--

DROP TABLE IF EXISTS `propriedade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `propriedade` (
  `prop_id` int(11) NOT NULL AUTO_INCREMENT,
  `prop_codigo` varchar(4) DEFAULT NULL,
  `prop_nome` varchar(255) DEFAULT NULL,
  `prop_endereco` varchar(255) DEFAULT NULL,
  `prop_municipio` varchar(255) DEFAULT NULL,
  `prop_uf` varchar(2) DEFAULT NULL,
  `prop_inscricao_estadual` varchar(255) DEFAULT NULL,
  `prop_cnpj` varchar(255) DEFAULT NULL,
  `prop_proprietario` varchar(255) DEFAULT NULL,
  `prop_area` decimal(10,2) DEFAULT NULL,
  `prop_atividade_principal` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`prop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `propriedade`
--

LOCK TABLES `propriedade` WRITE;
/*!40000 ALTER TABLE `propriedade` DISABLE KEYS */;
INSERT INTO `propriedade` VALUES (1,'0001','FAZENDA SANTA FE','BR 130 KM 87','ARAGUATINS','TO','1785901','11.111.0001/32','SR. TESTE',2000.50,'PECUARIA'),(2,'0002','ROÇA BUMERANGUE','TO 010 KM 130','AUGUSTINÓPOLIS','TO','5680956','22.222.0002/23','SR. FULANO',5678.22,'AGRICULTURA');
/*!40000 ALTER TABLE `propriedade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensor`
--

DROP TABLE IF EXISTS `sensor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sensor` (
  `sen_id` int(11) NOT NULL AUTO_INCREMENT,
  `sen_codigo` varchar(8) CHARACTER SET utf8 DEFAULT NULL,
  `sen_descricao` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `sen_nserie` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `sen_tipo` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `sen_medicao` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `sen_alcance` int(11) DEFAULT NULL,
  `sen_tecnologia` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Infravermelho, Radiofrequencia ou Telemetria',
  `sen_ativo` int(1) DEFAULT NULL,
  `sen_animal_fk` int(11) DEFAULT NULL,
  `sen_central_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`sen_id`),
  KEY `fk_sensor_animal_idx` (`sen_animal_fk`),
  KEY `fk_sensor_central_idx` (`sen_central_fk`),
  CONSTRAINT `fk_sensor_central1` FOREIGN KEY (`sen_central_fk`) REFERENCES `central` (`cen_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor`
--

LOCK TABLES `sensor` WRITE;
/*!40000 ALTER TABLE `sensor` DISABLE KEYS */;
INSERT INTO `sensor` VALUES (1,'00000001','Sensor 1',NULL,NULL,NULL,NULL,NULL,1,1,1),(2,'00000002','Sensor 2',NULL,NULL,NULL,NULL,NULL,0,0,1),(3,'00000003','Sensor 3',NULL,NULL,NULL,NULL,NULL,1,3,1),(4,'00000004','Sensor 4',NULL,NULL,NULL,NULL,NULL,0,NULL,1),(5,'00000005','Sensor 5',NULL,NULL,NULL,NULL,NULL,0,0,1),(6,'00000006','Sensor 6',NULL,NULL,NULL,NULL,NULL,0,NULL,1),(7,'00000007','Sensor 7',NULL,NULL,NULL,NULL,NULL,1,7,1),(8,'00000008','Sensor 8',NULL,NULL,NULL,NULL,NULL,0,NULL,1),(9,'00000009','Sensor 9',NULL,NULL,NULL,NULL,NULL,0,0,1),(10,'00000010','Sensor 10',NULL,NULL,NULL,NULL,NULL,0,NULL,1);
/*!40000 ALTER TABLE `sensor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `usu_id` int(11) NOT NULL AUTO_INCREMENT,
  `usu_login` varchar(255) DEFAULT NULL,
  `usu_senha` varchar(255) DEFAULT NULL,
  `usu_funcionario_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`usu_id`),
  KEY `fk_usuario_funcionario_idx` (`usu_funcionario_fk`),
  CONSTRAINT `fk_usuario_funcionario1` FOREIGN KEY (`usu_funcionario_fk`) REFERENCES `funcionario` (`func_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'teste1','e959088c6049f1104c84c9bde5560a13',1),(2,'teste2','38851536d87701d2191990e24a7f8d4e',2);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-02-03 23:16:58
