import re
import csv
import pandas as pd

# allow the printing of DataFrames to be larger than normal
pd.set_option('display.width', 200)

# title, publishedDate, dislikeCount, commentCount, viewCount, likeCount
videos_df = pd.read_csv("text3.csv")

# empty dictionary that will hold our final data
histogram = {}

# used as regex for removing non-alphanumeric characters
pattern = re.compile('[\W_]+')

print("Adding words to histogram")
for index, video in videos_df.iterrows():

    # get the title
    title = video["title"]

    # get each word in the title
    list_title = str(title).strip().lower().split(" ")

    # print(list_title)

    for word in list_title:

        # remove any non-alphanumeric character
        cleaned_word = pattern.sub('', word) # http://stackoverflow.com/questions/1276764/stripping-everything-but-alphanumeric-chars-from-a-string-in-python

        # print("cleaned_word: " + str(cleaned_word))

        # if cleaned_word is not the empty string
        if cleaned_word != "":
            # add word to the histogram_df
            if cleaned_word in histogram:
                histogram[cleaned_word] += 1
            else:
                histogram[cleaned_word] = 1

# print(histogram)

print("Translating histogram to DataFrame")
histogram_df = pd.DataFrame()

# translate histogram to a dataframe
for word in histogram:
    s = pd.Series([word, histogram[word], "06/01/2016"], index=["word", "count", "date"])

    histogram_df = histogram_df.append(s, ignore_index=True)

# save changes to a file
print("Saving to file histogram.csv")
histogram_df.to_csv("histogram.csv")