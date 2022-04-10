import timeit

ITERATIONS=10000
MILLISECONDS=1000

# set up
ENCRYPT_SETUP = '''
from cryptography.fernet import Fernet
key = Fernet.generate_key()
f = Fernet(key)
'''

DECRYPT_SETUP = '''
from cryptography.fernet import Fernet
key = Fernet.generate_key()
f = Fernet(key)
ciphertext = f.encrypt(b"Hello world")
'''

encryption_result = timeit.timeit(stmt="f.encrypt(b'Hello world')", setup=ENCRYPT_SETUP, number=ITERATIONS)

decryption_result = timeit.timeit(stmt="f.decrypt(ciphertext)", setup=DECRYPT_SETUP, number=ITERATIONS)

print("Encryption result...")
print(f"{(encryption_result / ITERATIONS) * MILLISECONDS} milliseconds")
print("Decryption result...")
print(f"{(decryption_result/ITERATIONS) * MILLISECONDS} milliseconds")

