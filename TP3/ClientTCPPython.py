import socket

ip = 'localhost'
port = 1234

client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client.connect((ip, port))
print("Client connected")

while (True) :
    message = input("Entrez votre ligne : ")
    client.send(message.encode())

    data = client.recv(8192)
    print(data.decode())