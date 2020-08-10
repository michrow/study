



# 概述

本文档主要描述 mvn  pom.xml全配置，记录学习如何真正理解会用maven构建打包项目。

# pom文件总览

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                      http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <!-- 基础配置-->
  <groupId>...</groupId>
  <artifactId>...</artifactId>
  <version>...</version>
  <packaging>...</packaging>
    
  <!-- 依赖配置 -->
  <dependencies>...</dependencies>
  <parent>...</parent>
  <dependencyManagement>...</dependencyManagement>
  <modules>...</modules>
  <properties>...</properties>

  <!-- 编译配置 -->
  <build>...</build>
  <reporting>...</reporting>

  <!-- 项目信息配置 -->
  <name>...</name>
  <description>...</description>
  <url>...</url>
  <inceptionYear>...</inceptionYear>
  <licenses>...</licenses>
  <organization>...</organization>
  <developers>...</developers>
  <contributors>...</contributors>
  <!-- 环境配置 -->
  <issueManagement>...</issueManagement>
  <ciManagement>...</ciManagement>
  <mailingLists>...</mailingLists>
  <scm>...</scm>
  <prerequisites>...</prerequisites>
  <repositories>...</repositories>
  <pluginRepositories>...</pluginRepositories>
  <distributionManagement>...</distributionManagement>
  <profiles>...</profiles>
</project>

```

# 基础配置

```xml
  <groupId>...</groupId>
  <artifactId>...</artifactId>
  <version>...</version>
  <packaging>...</packaging>
```



- **project** - `project` 是 pom.xml 中描述符的根。

- **modelVersion** - `modelVersion` 指定 pom.xml 符合哪个版本的描述符。maven 2 和 3 只能为 4.0.0。

- 唯一标识

  groupId、artifactId、version唯一标识一个jar.

  - groupId：团体、组织标识，一般为项目的包名

  - artifactId：独立项目标识

  - version：项目版本

    - maven 有自己的版本规范，一般是如下定义 major version、minor version、incremental version-qualifier ，比如 1.2.3-beta-01。要说明的是，maven 自己判断版本的算法是 major、minor、incremental 部分用数字比较，qualifier 部分用字符串比较，所以要小心 alpha-2 和 alpha-15 的比较关系，最好用 alpha-02 的格式。

    - maven 在版本管理时候可以使用几个特殊的字符串 SNAPSHOT、LATEST、RELEASE。比如 `1.0-SNAPSHOT`。各个部分的含义和处理逻辑如下说明：
      - **SNAPSHOT** - 这个版本一般用于开发过程中，表示不稳定的版本。
      - **LATEST** - 指某个特定构件的最新发布，这个发布可能是一个发布版，也可能是一个 snapshot 版，具体看哪个时间最后。
      - **RELEASE** ：指最后一个发布版。

  - packaging：项目类型，即打包之后的形式，默认jar，其他的还有war，pom

# 依赖配置

```xml
  <dependencies>...</dependencies>
  <parent>...</parent>
  <dependencyManagement>...</dependencyManagement>
  <modules>...</modules>
  <properties>...</properties>
```

## dependencies

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                      https://maven.apache.org/xsd/maven-4.0.0.xsd">
  ...
  <dependencies>
    <dependency>
     <groupId>org.apache.maven</groupId>
      <artifactId>maven-embedder</artifactId>
      <version>2.0</version>
      <type>jar</type>
      <scope>test</scope>
      <optional>true</optional>
      <exclusions>
        <exclusion>
          <groupId>org.apache.maven</groupId>
          <artifactId>maven-core</artifactId>
        </exclusion>
      </exclusions>
    </dependency>
    ...
  </dependencies>
  ...
</project>

```

- type：包类型，默认jar
- **scope**：指定类路径以及如何限制依赖关系的传递，（[专讲](#依赖-》scope)）
- **optional（*）**：避免依赖传递([专讲](#依赖-》optional))
- exclusions：排除不需要的依赖，和optional不同的是，exclusions是主动排除，optional是被动排除

## parent

```xml
  <parent>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>my-parent</artifactId>
    <version>2.0</version>
    <relativePath>../my-parent</relativePath>
  </parent>
```

- 项目继承的配置

  - **(*)模块的packaging类型为pom**

  - 子模块需要配置的父模块的信息

    groupId artifactId version relativePath

    其中 relativePath 标明父模块pom文件所在路径，默认在上一层目录下（../pom.xml，所以一般不需要配置），	如果根据relativePath找不到，则优先查找本地仓库，最后查找远程仓库。如果配置为空（ <relativePath/>）,则从仓库中查找。

- 可继承的配置（[专栏](#可继承配置)）

## dependencyManagement

主要目的是同一管理依赖包的版本，在dependencyManagement中声明的依赖不会实际引入（只是声明），子项目中如果需要引入依赖只需要使用groupId，artifactId即可。

import 依赖范围

- 将目标pom中的dependencyManagement配置导入，合并到当前dependencyManagement中

- 使用组合而非继承的方式引入配置

- import一般指向打包类型为pom的模块

  ```xml
  <dependencyManagement>
  		<dependencies>
  			<dependency>
  				<groupId>org.springframework.cloud</groupId>
  				<artifactId>spring-cloud-gateway-dependencies</artifactId>
  				<version>${project.version}</version>
  				<type>pom</type>
  				<scope>import</scope>
  			</dependency>
  			<dependency>
  				<groupId>org.springframework.cloud</groupId>
  				<artifactId>spring-cloud-commons-dependencies</artifactId>
  				<version>${spring-cloud-commons.version}</version>
  				<type>pom</type>
  				<scope>import</scope>
  			</dependency>
  		<dependencies>
  <dependencyManagement>
  
  ```

  

## modules

子模块列表

```xml
  <modules>
    <module>my-project</module>
    <module>another-project</module>
    <module>third-project/pom-example.xml</module>
  </modules>
```

## properties

定义属性，使用方式${property}

```xml
<project>
  ...
  <properties>
    <maven.compiler.source>1.7<maven.compiler.source>
    <maven.compiler.target>1.7<maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <jdk.version>1.8</jdk.version>
	<lombok.version>1.16.20</lombok.version>
  </properties>
  ...
</project>
```



# 编译配置

```xml
  <build>...</build>
  <reporting>...</reporting>
```

## build

build分为项目build和profile build

```xml
<build>
  <defaultGoal>install</defaultGoal>
  <directory>${basedir}/target</directory>
  <finalName>${artifactId}-${version}</finalName>
  <filters>
    <filter>filters/filter1.properties</filter>
  </filters>
  ...
  <resources></resources>
  <plugins></plugins>
</build>

```

- defaultGoal：缺省执行的目标或阶段
- directory：构建输出路径
- finalName：最终构建名称
- filters：

## resources：资源配置

```xml
 <resources>
      <resource>
          <targetPath>META-INF/plexus</targetPath>
          <filtering>false</filtering>
          <directory>${basedir}/src/main/plexus</directory>
          <includes>
              <include>configuration.xml</include>
          </includes>
          <excludes>
              <exclude>**/*.properties</exclude>
          </excludes>
      </resource>
  </resources>
  <testResources>
  </testResources>
```

- targetPath：打包路径
- filtering：是否启用过滤
- directory：源目录
- includes：包含的文件
- excludes：排除的文件，如果与includes冲突，以excludes生效
- testResources：指定测试阶段资源，默认为${basedir}/src/test/resources

## plugin 

> [maven常用插件](https://www.cnblogs.com/pixy/p/4977550.html)

```xml
<plugins>
      <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-jar-plugin</artifactId>
          <version>2.6</version>
          <extensions>false</extensions>
          <inherited>true</inherited>
          <configuration>
              <classifier>test</classifier>
          </configuration>
          <dependencies>...</dependencies>
          <executions>...</executions>
      </plugin>
  </plugins>
```

- extensions: true or false，是否装载插件扩展。默认false

- inherited: true or false，是否此插件配置将会应用于poms，那些继承于此的项目

- configuration: 指定插件配置

- dependencies: 插件需要依赖的包

- executions: 用于配置execution目标，一个插件可以有多个目标。

  ```xml
  <plugin>  
          <artifactId>maven-antrun-plugin</artifactId>  
          <executions>  
            <execution>  
              <id>echodir</id>  
              <goals>  
                  <goal>run</goal> 
              </goals>  
              <phase>verifyphase>  
              <inherited>falseinherited>  
              <configuration>  
                <tasks>  
                  <echo>Build Dir: ${project.build.directory}</echo>  
                </tasks>  
              </configuration>  
            </execution>  
          </executions>  
        </plugin>
  ```

  - id:规定execution 的唯一标志
  - goals: 表示目标
  - phase: 表示阶段，目标将会在什么阶段执行
  - inherited: 和上面的元素一样，设置false maven将会拒绝执行继承给子插件
  - configuration: 表示此执行的配置属性

## pluginManagement

与 `dependencyManagement` 很相似，在当前 POM 中仅声明插件，而不是实际引入插件。子 POM 中只配置 `groupId` 和 `artifactId` 就可以完成插件的引用，且子 POM 有权重写 pluginManagement 定义。它的目的在于统一所有子 POM 的插件版本。

- 父pom插件管理配置

```xml
<pluginManagement>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-source-plugin</artifactId>
            <version>3.2.1</version>
            <executions>
                <execution>
                	<id>attach-source</id>
                    <phase>verify</phase>
                    <goals>
                    	<goal>jar-no-fork</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</pluginManagement>
```

- 子pom插件配置

```xml
<build>
	<plugins>
    	<plugin>
        	<groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-source-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

# 项目信息配置

```xml
  <!--项目名-->
  <name>maven-notes</name>
 
<!--项目描述-->
  <description>maven 学习笔记</description>
 
<!--项目url-->
  <url>https://github.com/dunwu/maven-notes</url>
 
<!--项目开发年份-->
  <inceptionYear>2017</inceptionYear>
  
<!--开源协议-->
  <licenses>
    <license>
      <name>Apache License, Version 2.0</name>
      <url>https://www.apache.org/licenses/LICENSE-2.0.txt</url>
      <distribution>repo</distribution>
      <comments>A business-friendly OSS license</comments>
    </license>
  </licenses>
 
<!--组织信息(如公司、开源组织等)-->
  <organization>
    <name>...</name>
    <url>...</url>
  </organization>

<!--开发者列表-->
  <developers>
    <developer>
      <id>victor</id>
      <name>Zhang Peng</name>
      <email>forbreak at 163.com</email>
      <url>https://github.com/dunwu</url>
      <organization>...</organization>
      <organizationUrl>...</organizationUrl>
      <roles>
        <role>architect</role>
        <role>developer</role>
      </roles>
      <timezone>+8</timezone>
      <properties>...</properties>
    </developer>
  </developers>

<!--代码贡献者列表-->
   <contributors>
    <contributor>
      <!--标签内容和<developer>相同-->
    </contributor>
  </contributors>


```

#  环境配置

```xml
  <issueManagement>...</issueManagement>
  <ciManagement>...</ciManagement>
  <mailingLists>...</mailingLists>
  <scm>...</scm>
  <prerequisites>...</prerequisites>
  <repositories>...</repositories>
  <pluginRepositories>...</pluginRepositories>
  <distributionManagement>...</distributionManagement>
  <profiles>...</profiles>
```

```xml
<!--缺陷跟踪系统-->
<issueManagement>
     <system>GitHub Issues</system>
     <url>https://github.com/Dromara/soul/issues</url>
 </issueManagement>
 
<!-- 通知机制以及被通知的邮箱-->
  <ciManagement>
    <system>continuum</system>
    <url>http://127.0.0.1:8080/continuum</url>
    <notifiers>
      <notifier>
        <type>mail</type>
        <sendOnError>true</sendOnError>
        <sendOnFailure>true</sendOnFailure>
        <sendOnSuccess>false</sendOnSuccess>
        <sendOnWarning>false</sendOnWarning>
        <configuration><address>continuum@127.0.0.1</address></configuration>
      </notifier>
    </notifiers>
  </ciManagement>
  <mailingLists>
    <mailingList>
      <name>User List</name>
      <subscribe>user-subscribe@127.0.0.1</subscribe>
      <unsubscribe>user-unsubscribe@127.0.0.1</unsubscribe>
      <post>user@127.0.0.1</post>
      <archive>http://127.0.0.1/user/</archive>
      <otherArchives>
        <otherArchive>http://base.google.com/base/1/127.0.0.1</otherArchive>
      </otherArchives>
    </mailingList>
  </mailingLists>
 <!--版本控制-->
 <scm>
     <url>https://github.com/Dromara/soul.git</url>
     <connection>scm:git:https://github.com/Dromara/soul</connection>
     <developerConnection>scm:git:git@github.com:Dromara/soul</developerConnection>
     <tag>${project.version}</tag>
 </scm>
<!--pom执行的前置条件-->
<prerequisites>
	<maven>2.0.6</maven>
</prerequisites>
  
 <!--发布到私服-->
 <distributionManagement>
        <snapshotRepository>
            <id>ossrh</id>
            <url>https://oss.sonatype.org/content/repositories/snapshots</url>
        </snapshotRepository>
        <repository>
            <id>ossrh</id>
            <url>https://oss.sonatype.org/service/local/staging/deploy/maven2/</url>
        </repository>
    </distributionManagement>
<!--activation 是一个 profile 的关键。配置文件的功能来自于在某些情况下仅修改基本 POM 的功能。这些情况通过 activation 元素指定。-->

<profiles>
    <profile>
        <id>disable-java8-doclint</id>
        <!--jdk1.8及其以上才需要additionalparam配置-->
        <activation>
            <jdk>[1.8,)</jdk>
        </activation>
        <properties>
            <additionalparam>-Xdoclint:none</additionalparam>
        </properties>
    </profile>
</profiles>
 
```

- [发布到私服](#发布)

# 专栏

## 依赖-》scope 

- 依赖的Scope
  scope定义了类包在项目的使用阶段。项目阶段包括： 编译，运行，测试和发布。

- 分类说明

  - compile
    默认scope为compile，表示为当前依赖参与项目的编译、测试和运行阶段，属于强依赖。打包之时，会达到包里去。
  - test
    该依赖仅仅参与测试相关的内容，包括测试用例的编译和执行，比如定性的Junit。
  - runtime
    依赖仅参与运行周期中的使用。一般这种类库都是接口与实现相分离的类库，比如JDBC类库，在编译之时仅依赖相关的接口，在具体的运行之时，才需要具体的mysql、oracle等等数据的驱动程序。
    此类的驱动都是为runtime的类库。
  - provided
    该依赖在打包过程中，不需要打进去，这个由运行的环境来提供，比如tomcat或者基础类库等等，事实上，该依赖可以参与编译、测试和运行等周期，与compile等同。区别在于打包阶段进行了exclude操作。
  - system
    使用上与provided相同，不同之处在于该依赖不从maven仓库中提取，而是从本地文件系统中提取，其会参照systemPath的属性进行提取依赖。
  - import
    这个是maven2.0.9版本后出的属性，import只能在dependencyManagement的中使用，能解决maven单继承问题，import依赖关系实际上并不参与限制依赖关系的传递性。
  - systemPath
    当maven依赖本地而非repository中的jar包，sytemPath指明本地jar包路径,例如：

  ```xml
  <dependency>
      <groupid>org.hamcrest</groupid>
      <artifactid>hamcrest-core</artifactid>
      <version>1.5</version>
      <scope>system</scope>
      <systempath>${basedir}/WebContent/WEB-INF/lib/hamcrest-core-1.3.jar</systempath>
  </dependency>
  ```

  

## 依赖-》optional

maven中依赖关系具有传递性，管理依赖关系的传递性可以有两种方式管理，**可选依赖**和**排除依赖**

- optional

  假设，项目A依赖项目B，项目B依赖项目C即 A->B->C，项目B引入C的pom文件如下：

  ```xml
      <dependency>
        <groupId>sample.ProjectC</groupId>
        <artifactId>Project-C</artifactId>
        <version>1.0</version>
        <optional>true</optional> <!-- value will be true or false only -->
      </dependency>
  ```

  optional设置为true的作用就是，当项目A再引入项目B时，就不会再引入项目C

- excludes

  还是上述假设，如果项目A引入项目B，不想引入项目C，则pom配置文件如下：

  ```xml
   <dependency>
       <groupId>sample.ProjectB</groupId>
       <artifactId>Project-B</artifactId>
       <version>1.0</version>
   	 <exclusions>
           <exclusion>
           	 <groupId>sample.ProjectC</groupId>
       		 <artifactId>Project-C</artifactId>
           </exclusion>
       </exclusions>
      </dependency>
  ```

## parent

- 可继承配置

  ```
  (*) groupId：项目组ID，项目坐标的核心元素
  (*) version：项目版本，项目坐标的核心元素
      description：项目的描述信息
      organization：项目的组织信息
      inceptionYear：项目的创始年份
      url：项目的url地址
      developers：项目的开发者信息
      contributors：项目的贡献者信息
      distributionManagement：项目的部署配置信息
      issueManagement：项目的缺陷跟踪系统信息
      ciManagement：项目的持续集成系统信息
      scm：项目的版本控制系统信息
      mailingLists：项目的邮件列表信息
  (*) properties：自定义的maven属性配置信息
  (*) dependencyManagement：项目的依赖管理配置
  	repositories：项目的仓库配置
  (*) build：包括项目的源码目录配置、输出目录配置、插件配置、插件管理配置等信息
  	reporting：包括项目的报告输出目录配置、报告插件配置等信息
  ```

## 环境配置

### 发布

1、pom文件中配置distributionManagement

2、settings.xml中配置仓库用户名和密码

**注意：setting中的id必须与distributionManagement中仓库的id保持一致**

```xml
  <servers>
      <server>
        <id>releases</id>
        <username>android-jinchuang</username>
        <password>jinchuang</password>
      </server>
      <server>
        <id>snapshots</id>
        <username>android-jinchuang</username>
        <password>jinchuang</password>
      </server>
  </servers>
```

3、执行mvn:deploy

部署第三方jar:

```xml
  mvn deploy:deploy-file 
  -DgroupId=com.aliyun.oss 
  -DartifactId=aliyun-sdk-oss 
  -Dversion=2.2.3 
  -Dpackaging=jar 
  -Dfile=D:\aliyun-sdk-oss-2.2.3.jar 
  -Durl=http://192.168.1.221:8081/repository/3rd/
  -DrepositoryId=3rd-releases
```



# 案例

[springcloud gateway](https://github.com/spring-cloud/spring-cloud-gateway/blob/master/pom.xml)

# 相关链接

Maven 教程之 pom.xml 详解【[link1](https://juejin.im/post/5cb5cc0ff265da0359486892#heading-16), [link2](https://segmentfault.com/a/1190000021366568)】

Maven pom.xml scope 【[link1](https://blog.csdn.net/blueheart20/article/details/81014116)】

# Maven常用命令

```xml
mvn compile 编译源代码
mvn test-compile 编译测试代码
mvn test 运行测试
mvn package 打包，根据pom.xml打成war或jar
    > 如果pom.xml中设置 war，则此命令相当于mvn war:war
    >  如果pom.xml中设置 jar，则此命令相当于mvn jar:jar
mvn -Dtest package 打包但不测试。完整命令为：mvn -D maven.test.skip=true package
mvn install 在本地Repository中安装jar
mvn clean 清除产生的项目
mvn eclipse:eclipse 生成eclipse项目
mvn idea:idea 生成idea项目
mvn eclipse:clean 清除eclipse的一些系统设置
mvn dependency:sources 下载源码
```



