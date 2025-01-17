# DES and its Application

## Background
The DES algorithm is known as the Data Encryption Algorithm (DEA). It is a bit-oriented and shared-key algorithm. They are based on 64 bits. In other words, each text—including plain text, encrypted text, and key—is split up into blocks, each of which is 64 bits. Although key should only be 8 bytes long, we could still obtain the key. The same key or password is used for both encryption and decryption. Encryption and decryption steps will be described briefly in the remaining paragraphs.

Since it uses a shared-key technique, both the encryption and decryption processes use the same secret key stages. 16 sub-keys will be computed for it. Every sub-key will be assigned to the appropriate wheel function.

On the one hand, encryption is composed of the initial permutation the wheel function with 16 times, as well as the inverse initial permutation. As I mentioned above, each wheel function receives a sub-key. It is important that the order of putting sub-key into the wheel function decide whether it is encryption or decryption.

The identical permutation and wheel functions are used for decryption instead, but the sub-keys are arranged in the opposite direction from how they were formed.

## Requirements and features
Requirements:
1.	User could enter freely regardless of length limitation of plain/cipher text or key.
2.	User could use the friendly interface to select file from file system or type the plain text to encrypt/decrypt.
3.	There are two independent components that show the encryption and decryption, respectively.
4.	DES algorithm is implemented correctly.

## Details
Please see the [instructions of DES](./DES.pdf).

# Secrect Diary encrypted by DES

## Background
A diary has always been a particularly private thing for a person, which is why most people don't want to share it with others, or will only share it with a small group of people close to them. However, after surveying people around me who used to keep a diary, I discovered that most of them had their diaries watched or snooped on by their parents, which seriously discouraged them from keeping a diary and sticking to a good habit. This is overwhelmingly influenced by Chinese educational beliefs - such as that children should not have privacy, or that adults use the excuse of wanting to know more about them without respecting them. Based on the need to resist this invasion of privacy, I used my in-class knowledge of security and privacy to implement a secure diary, namely It Keeps Secret.

## Technology
1. DES
The DES algorithm is known as the Data Encryption Algorithm (DEA). It is a bit-oriented and shared-key algorithm. They are based on 64 bits. In other words, each text—including plain text, encrypted text, and key—is split up into blocks, each of which is 64 bits. Although key should only be 8 bytes long, we could still obtain the key. The same key or password is used for both encryption and decryption. Encryption and decryption steps will be described briefly in the remaining paragraphs.
2. Hash Function
The National Security Agency created the cryptographic hash function SHA-1, which the National Institute of Standards and Technology (NIST) released as a Federal Information Processing Standard (FIPS). A message digest, which is produced using SHA-1, is a 160-bit (20-byte) hash value that is commonly shown as 40 hexadecimal digits.

## Details
Please see the [instructions of Diary](./Application.pdf).
