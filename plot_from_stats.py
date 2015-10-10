#!/usr/bin/python

from __future__ import division
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.gridspec as gridspec
#from matplotlib.backends.backend_pdf import PdfPages
import sys

def stats_file_as_matrix(file_name):
    with open(file_name, 'r') as f:
        return [ map(float,line.strip().split(' ')) for line in f ]
#pdfTitle = 'results.pdf'
#pp = PdfPages(pdfTitle)
titles = ["Bitrate", "Delay", "Jitter", "Packet loss"]

for f in sys.argv[1:]:
	print("Starting work on "+f+", converting stats to matrix!")
	mat = stats_file_as_matrix(f)

	x = range(len(mat))


	#define the figure size and grid layout properties
	figsize = (10, 8)
	cols = 2
	rows = 2
	gs = gridspec.GridSpec( rows, cols)

	fig = plt.figure(num=1, figsize=figsize)
	fig.suptitle(f)
	ax = []
	for i in range(4):
		y = map(lambda r:r[i+1],mat)
		row = (i // cols)
		col = i % cols
		ax.append(fig.add_subplot(gs[row, col]))
		ax[-1].set_title(titles[i])
		ax[-1].set_xlabel('Time [ms]')
		ax[-1].plot(x, y, 'o', ls='-', ms=4)
	print("Finished with "+f+", creating JPG!")
	#pp.savefig(fig)
	plt.savefig(f+'.jpg')
	plt.clf()

#pp.close()

