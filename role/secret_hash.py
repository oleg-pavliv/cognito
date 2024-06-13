import hmac
import hashlib
import base64
import argparse

def get_secret_hash(username, client_id, client_secret):
    message = username + client_id
    dig = hmac.new(client_secret.encode('utf-8'), message.encode('utf-8'), hashlib.sha256).digest()
    return base64.b64encode(dig).decode()

def main():
    parser = argparse.ArgumentParser(description='Generate a secret hash.')
    parser.add_argument('username', type=str, help='The username')
    parser.add_argument('client_id', type=str, help='The client ID')
    parser.add_argument('client_secret', type=str, help='The client secret')
    
    args = parser.parse_args()
    
    secret_hash = get_secret_hash(args.username, args.client_id, args.client_secret)
    print(secret_hash)

if __name__ == '__main__':
    main()
