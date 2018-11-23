from tenacity import retry, RetryError, wait_fixed, retry_if_exception_type, stop_after_delay, stop_after_attempt
import urllib.request

cnt = 0

def get_inexistent_site():
    global cnt
    cnt = cnt + 1
    print('Try #' + str(cnt) + "...")
    contents = urllib.request.urlopen("http://exampleee.com/foo/bar").read()
    print(contents)

@retry(wait=wait_fixed(1), # wait 1 second between retries
       retry=retry_if_exception_type(IOError), # Only retry if IOError
       stop=(stop_after_delay(10) | stop_after_attempt(7) ) # 10 seconds or 7 attempts
)
def get_inexistent_site_and_retry():
    get_inexistent_site()

try:
    get_inexistent_site_and_retry()
except RetryError:
    print('RetryError occurred')
