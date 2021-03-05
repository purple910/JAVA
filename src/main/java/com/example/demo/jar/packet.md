# idea打包

## 普通的java项目
```
Project Structure -> Artifacts -> add jar -> From modules with dependencies - > Main class
Build -> Build Artifacts
```

## 普通的maven项目
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.8.1</version>
    <configuration>
        <source>8</source>
        <target>8</target>
    </configuration>
</plugin>
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.2.0</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <transformers>
                    <transformer
                            implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                        <!-- 主类的位置，例如上图文件，主类配置应为： -->
                        <mainClass>com.tfswx.demo.Main</mainClass>
                    </transformer>
                </transformers>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## maven项目键依赖打包
```
<plugins>
    <plugin>
        <artifactId>maven-assembly-plugin</artifactId>
        <configuration>
            <archive>
                <manifest>
                    <mainClass>org.example.code.CodeBuilder</mainClass>
                </manifest>
            </archive>
            <descriptorRefs>
                <descriptorRef>jar-with-dependencies</descriptorRef>
            </descriptorRefs>
            <appendAssemblyId>false</appendAssemblyId>
        </configuration>
    </plugin>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
            <source>8</source>
            <target>8</target>
        </configuration>
    </plugin>
</plugins>
```

## maven项目添加配置文件
```
<resources>
    <resource>
        <directory>src/main/resources</directory>
        <includes>
            <include>template/*.*</include>
        </includes>
        <filtering>false</filtering>
    </resource>
</resources>
```