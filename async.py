import asyncio
from unittest import result
import aiohttp
import time

SIZE_TO_SEND=1000
async def main():
    url = 'http://localhost:8080/processing/v1/async'
    files = {'file': open('8k.jpg', 'rb')}
    async with aiohttp.ClientSession() as session:
        return await session.post(url, data=files)


async def run():
    results = await asyncio.gather(*[main() for i in range(SIZE_TO_SEND)])


if __name__ == "__main__":
    start = time.time()
    asyncio.run(run())
    end = time.time()
    print("Elapsed time during the whole program in seconds:",
                                         end-start)