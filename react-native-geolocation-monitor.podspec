require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-geolocation-monitor"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-geolocation-monitor
                   DESC
  s.homepage     = "https://github.com/abondarevrn/react-native-geolocation-monitor"
  s.license      = "MIT"
  # s.license    = { :type => "MIT", :file => "FILE_LICENSE" }
  s.authors      = { "Andriy" => "andriy@mr-apps.com" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/abondarevrn/react-native-geolocation-monitor.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  s.dependency 'AFNetworking', '~> 3.0'
  # s.dependency "..."
end

