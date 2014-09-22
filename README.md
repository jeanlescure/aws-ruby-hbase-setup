# Ruby HBase JAR Initializer for AWS Clusters

### Usage

* Compile and upload the resulting JAR to an S3 bucket.
* When creating a MapReduce cluster on AWS, on the *Steps* section, add a _custom JAR_ step and make sure it points to the S3 bucket route.

### Compiling

`
$ javac src/com/aws/RubyHbase/Setup/*.java src/com/aws/RubyHbase/util/*.java
$ jar cmfv Manifest.txt ruby-hbase-setup.jar -C src com
`

### Running Manually

`$ java -jar ruby-hbase-setup.jar`