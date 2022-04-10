# frozen_string_literal: true

require "benchmark"
require "openssl"

MILLISECONDS = 1000

key = OpenSSL::PKey::RSA.new 2048
message = "hello world"
ciphertext = key.public_encrypt(message)

encryption_time = Benchmark.measure do
  10000.times { key.public_encrypt(message) }
end

decryption_time = Benchmark.measure do
  10000.times { key.private_decrypt(ciphertext) }
end

puts "Encryption result... #{(encryption_time.real / 10000) * MILLISECONDS} milliseconds"
puts "Decryption result... #{(decryption_time.real / 10000) * MILLISECONDS} milliseconds"
