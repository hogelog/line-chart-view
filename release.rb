#!/usr/bin/env ruby
gradle_properties = File.read("gradle.properties")

unless gradle_properties =~ /^VERSION_NAME=(.+)-SNAPSHOT$/
  STDERR.puts "Invalid VERSION_NAME"
  exit 1
end

version_name = $1
version_snapshot = "#{version_name}-SNAPSHOT"

unless gradle_properties =~ /^VERSION_CODE=(\d+)$/
  STDERR.puts "Invalid VERSION_CODE"
  exit 1
end

version_code = $1.to_i

gradle_properties.gsub!("#{version_name}-SNAPSHOT", version_name)

File.write("gradle.properties", gradle_properties)

system("git commit -a -m 'Release #{version_name}'") || exit(1)
system("git tag #{version_name}") || exit(1)
system("cd line-chart-view && ../gradlew clean uploadArchives")

print "Type next version: "
next_version = STDIN.readline.chomp
next_version_snapshot = "#{next_version}-SNAPSHOT"
gradle_properties.gsub!("VERSION_NAME=#{version_name}", "VERSION_NAME=#{next_version_snapshot}")
gradle_properties.gsub!("VERSION_CODE=#{version_code}", "VERSION_CODE=#{version_code + 1}")
File.write("gradle.properties", gradle_properties)

readme = File.read("README.md")
readme.gsub!(/org.hogel:line-chart-view:\d+\.\d+\.\d+/, "org.hogel:line-chart-view:#{version_name}")
readme.gsub!(/<version>\d+\.\d+\.\d+<\/version>/, "<version>#{version_name}</version>")
readme.gsub!(/org.hogel:line-chart-view:\d+\.\d+\.\d+-SNAPSHOT/, "org.hogel:line-chart-view:#{next_version_snapshot}")
File.write("README.md", readme)

system("git commit -a -m 'Next version #{next_version}'") || exit(1)
system("cd line-chart-view && ../gradlew clean uploadArchives")
