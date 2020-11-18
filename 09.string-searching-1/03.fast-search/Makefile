PROG_SRC := FastSearch.scala

PROG_CLASS := $(patsubst %.scala,%.class,$(PROG_SRC))
PROG_NAME := $(patsubst %.scala,%,$(PROG_SRC))


.PHONY: all test clean


all: FastSearch.class

FastSearch.class: FastSearch.scala mooc/EdxIO.class
	scalac -classpath . $<

mooc/EdxIO.class: mooc/EdxIO.java
	make -C mooc


test: all
	@for if in input??.txt; do \
		of="output$$(echo $$if | cut -c 6-)" ; \
		cp $$if input.txt ; \
		scala $(PROG_NAME) ; \
		cmp $$of output.txt > /dev/null ; \
		if [[ $$? -eq 0 ]]; then \
			echo -e "\033[0;32mTest $$if passed\033[0m" ; \
		else \
			echo -e "\033[0;31mTest $$if failed\033[0m" ; \
			diff output.txt $$of ; \
			exit 1 ; \
		fi ; \
	done ;
	@rm -f input.txt output.txt


clean:
	rm -f *~
	rm -f *.class
	rm -f input.txt output.txt
	make -C mooc clean
