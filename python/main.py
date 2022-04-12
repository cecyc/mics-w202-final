import timeit

ITERATIONS=10000
MILLISECONDS=1000

# set up
ENCRYPT_SETUP = '''
from cryptography.hazmat.primitives.asymmetric import rsa
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.asymmetric import padding

private_key = rsa.generate_private_key(
    public_exponent=65537,
    key_size=2048,
)
public_key = private_key.public_key()
message = b"Hello world"
'''

DECRYPT_SETUP = '''
from cryptography.hazmat.primitives.asymmetric import rsa
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.asymmetric import padding

private_key = rsa.generate_private_key(
    public_exponent=65537,
    key_size=2048,
)
public_key = private_key.public_key()
ciphertext = public_key.encrypt(
    b"Hello world",
    padding.OAEP(
        mgf=padding.MGF1(algorithm=hashes.SHA256()),
        algorithm=hashes.SHA256(),
        label=None
    )
)
'''

def encrypt(public_key, message, padding, hashes):
  public_key.encrypt(
    message,
    padding.OAEP(
        mgf=padding.MGF1(algorithm=hashes.SHA256()),
        algorithm=hashes.SHA256(),
        label=None
    )
)



def decrypt(private_key, ciphertext, padding, hashes):
  private_key.decrypt(
    ciphertext,
    padding.OAEP(
        mgf=padding.MGF1(algorithm=hashes.SHA256()),
        algorithm=hashes.SHA256(),
        label=None
    )
)


encryption_result = timeit.timeit(stmt="encrypt(public_key, message, padding, hashes)", setup=ENCRYPT_SETUP, number=ITERATIONS, globals=globals())

decryption_result = timeit.timeit(stmt="decrypt(private_key, ciphertext, padding, hashes)", setup=DECRYPT_SETUP, number=ITERATIONS, globals=globals())

print("Encryption result...")
print(f"{(encryption_result / ITERATIONS) * MILLISECONDS} milliseconds")
print("Decryption result...")
print(f"{(decryption_result/ITERATIONS) * MILLISECONDS} milliseconds")

