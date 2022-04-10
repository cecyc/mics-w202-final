# frozen_string_literal: true

require "openssl"
require "base64"

PRIVATE_KEY = "private_key.txt"
PUBLIC_KEY = "public_key.txt"

if !File.exist?(PRIVATE_KEY) && !File.exist?(PUBLIC_KEY)
  # generate a new key if none exists
  key = OpenSSL::PKey::RSA.new 2048

  # export keys to txt -- for example purposes only, you would not want to keep these in plain text just laying around ;)
  File.open(PRIVATE_KEY, "w+") { |f| f.write(key.export) }
  File.open(PUBLIC_KEY, "w+") { |f| f.write(key.public_key) }
end

# load keys
private_key = OpenSSL::PKey::RSA.new(File.open(PRIVATE_KEY))
public_key = OpenSSL::PKey::RSA.new(File.open(PUBLIC_KEY))

puts "input a message to encrypt"

message = gets.chomp
ciphertext = Base64.encode64(public_key.public_encrypt(message))

puts "the encrypted ciphertext is ..."
puts ciphertext

# decrypt message
puts "the decrypted ciphertext is..."
decrypted = private_key.private_decrypt(Base64.decode64(ciphertext))
puts decrypted

# sign message
signature = Base64.encode64(private_key.sign_pss("SHA256", message, salt_length: :max, mgf1_hash: "SHA256"))
puts "Encrypted signature..."
puts signature

# verify signature
puts "verifying signature ..."
puts public_key.verify_pss("SHA256", Base64.decode64(signature), message, salt_length: :auto, mgf1_hash: "SHA256")
