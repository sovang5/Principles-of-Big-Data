from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream

# Go to http://dev.twitter.com and create an app.
# The consumer key and secret will be generated for you after
consumer_key="wWvrI2oofh8IU2438NIJWEXkA"
consumer_secret="HqHVeRsOAiENdDGOzLPazLYq3v9zCm9IzYkKe6DKZztSYbf1E9"

# After the step above, you will be redirected to your app's page.
# Create an access token under the the "Your access token" section
access_token="773746712238231552-l21hNQ9Qxw5JpN4tZrI1GAHRbUYGGaM"
access_token_secret="AQ4FlbC3AtSP9X3DdDbl8KJAeMW4iQweSEShd5qS7yJHP"

class StdOutListener(StreamListener):
    """ A listener handles tweets are the received from the stream.
    This is a basic listener that just prints received tweets to stdout.

    """
    def on_data(self, data):
        try:
            text_val=data.split(',"text":"')[1].split('","source')[0]
            print text_val
            saveFile=open('twitDB3.csv','a')
            saveFile.write(text_val)
            saveFile.write('\n')
            saveFile.close()
            return True
        except BaseException, e:
            print  'failed to load  data,',str(e)
    def on_error(self, status):
        print status

if __name__ == '__main__':
    l = StdOutListener()
    auth = OAuthHandler(consumer_key, consumer_secret)
    auth.set_access_token(access_token, access_token_secret)

    stream = Stream(auth, l)
    stream.filter(track=['a'])

