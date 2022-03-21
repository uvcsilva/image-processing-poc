import requests
import time

SIZE_TO_SEND=1000
count = 0
start = time.time()
for i in range(SIZE_TO_SEND):
  url = "http://localhost:8080/processing/v1/sync"

  files = {'file': open('8k.jpg','rb')}

  body = requests.post(url, files=files)
  count += 1
  print('PROGRESSO: {:.2f}%'.format((count/SIZE_TO_SEND)*100))

end = time.time()
print("Elapsed time during the whole program in seconds:",
                                         end-start)