import socket
import time
import threading
from threading import Thread

msgs = []

#client python 
def client(nb_client):
    client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    client.connect(("localhost", 1234))
    client.send(b"Message client Python")
    client.close()
    print("Client Python fini !")
    return


#serveur python
def serveur():
    serveur = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    serveur.bind(("localhost", 1234))
    print("Server python started")
    for i in range (2):
        msg = serveur.recv(1024)
        print(msg.decode())
        msgs.append(msg)
    serveur.close()

    if (len(msgs) == 2):
        print("Auto-test Python terminé !")
    else:
        print("Problème sur les messages du serveur Python")
    print("Fini !")

    return

client("1")
time.sleep(1)
serveur_thread = Thread(target=serveur, args=())
client_thread = Thread(target=client, args=("2"))

serveur_thread.start()
client_thread.start()
