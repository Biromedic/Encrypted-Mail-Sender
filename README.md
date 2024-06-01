# Secure Email Attachments Project

This repository contains three distinct projects demonstrating different approaches to securing email attachments using cryptographic techniques. Each project showcases a unique method for generating and exchanging session keys, and encrypting/decrypting email attachments.

## Overview

The three projects implemented in this repository demonstrate the following approaches to secure email attachments:

1. **Diffie-Hellman Key Exchange**: This approach uses the Diffie-Hellman (DH) key exchange to securely generate a shared secret key between the sender and receiver. This shared secret key is then used to encrypt and decrypt email attachments using the AES algorithm.
2. **RSA-Encrypted Session Keys**: This approach uses RSA encryption to securely exchange session keys. The session keys are then used to encrypt and decrypt email attachments using the AES algorithm.
3. **Random Session Keys with Public Key Encryption**: This approach generates random session keys, which are then exchanged using RSA encryption. These session keys are used to encrypt and decrypt email attachments using the AES algorithm.

## Implementation Details

- **Key Exchange**: Depending on the approach, either Diffie-Hellman or RSA algorithms are used to generate and exchange keys.
- **Symmetric Encryption**: AES algorithm is used for encrypting and decrypting email attachments in all three approaches.

## How to Use

### Get Public Key

To retrieve the public key for key exchange:
```http
GET /api/email/public-key
```

#### Send Encrypted Email

To send an encrypted email:

```http
POST /api/email/send
{
    "to": "receiver@example.com",
    "subject": "Test Subject",
    "body": "This is a test email",
    "file": [file attachment],
    "recipientPublicKey": "base64-encoded-recipient-public-key"
}
```
#### Decrypt Attachment

To decrypt an email attachment:

```http
POST /api/email/decrypt
{
    "encryptedAttachment": "base64-encoded-encrypted-attachment",
    "senderPublicKey": "base64-encoded-sender-public-key",
    "encryptedSessionKey": "base64-encoded-encrypted-session-key"  // Required for RSA approaches
}
```
